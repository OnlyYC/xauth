<template>
<div class="site-wrapper site-page--login">
  <el-row class="full">
    <el-col :span="18" class="full">
      <div class="brand-info" v-show='faceStatus!=1'>
        <h2 class="brand-info__text">XAuth</h2>
      </div>
      <div id="cloudwalk-obj" v-show='faceStatus==1'>

      </div>
    </el-col>

    <el-col :span="6" class="full">
      <div class="login-main full">
        <el-tabs v-model="activeName" :before-leave="handleSwitchTab">
          <el-tab-pane label="刷脸登录" name="first">
            <template v-if='faceStatus==0'>
              <el-form :model="faceForm" :rules="faceRule" ref="faceForm" @keyup.enter.native="faceFormNext()" status-icon>
                <el-form-item prop="principal">
                  <el-input v-model="faceForm.principal" placeholder="邮箱或用户名"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button class="login-btn-submit" type="primary" @click="faceFormNext()">刷脸</el-button>
                </el-form-item>
              </el-form>
            </template>
            <template v-if='faceStatus==1'>
              <div>刷脸中...</div>
              <div>{{faceDetectFailMessage}}</div>
            </template>
            <template v-if='faceStatus==2'>
              <div>识别中...</div>
              <img class="face-small-preview" v-bind:src="bestImageData"/>
            </template>
            <template v-if='faceStatus==20'>
              <div>{{identifyUsername}}</div>
              <el-form :model="faceLoginForm" :rules="faceLoginRule" ref="faceLoginForm" status-icon>
                <el-form-item>
                  <el-button class="login-btn-submit" type="primary" @click="faceFormSubmit()">登录</el-button>
                </el-form-item>
              </el-form>
            </template>
            <template v-if='faceStatus==21'>
              <div>{{identifyResultMessage}}</div>
              <div>
                <el-button class="login-btn-submit" type="primary" @click="resetLogin()">返回</el-button>
              </div>
            </template>
          </el-tab-pane>
          <el-tab-pane label="账号密码" name="second">
            <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" status-icon>
              <el-form-item prop="userName">
                <el-input v-model="dataForm.userName" placeholder="帐号"></el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input v-model="dataForm.password" type="password" placeholder="密码"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button class="login-btn-submit" type="primary" @click="dataFormSubmit()">登录</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </div>

    </el-col>
    </el-row>
</div>
</template>

<script>
  import { getUUID } from '@/utils'
  import {FaceLogin} from '@/utils/faceDetect'
  export default {
    data () {
      return {
        activeName: 'first',
        dataForm: {
          userName: '',
          password: '',
          uuid: '',
          captcha: ''
        },
        dataRule: {
          userName: [
            { required: true, message: '帐号不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '密码不能为空', trigger: 'blur' }
          ],
          captcha: [
            { required: true, message: '验证码不能为空', trigger: 'blur' }
          ]
        },
        faceForm: {
          principal: '',
        },
        faceStatus: 0, //刷脸状态 0   1 刷脸中   2刷脸结束
        faceRule: {
          principal: [
            { required: true, message: '邮箱、用户名不能为空', trigger: 'blur' }
          ],
        },
        faceLoginForm: {
          faceCredentials: ''
        },
        faceLoginRule: {
          faceCredentials: [
            { required: true, message: '人脸凭证不能为空', trigger: 'blur' }
          ],
        },
        identifyUsername: '',
        captchaPath: '',
        cloudwalkObjWidth: 0,
        cloudwalkObjHeight: 0,
        faceDetect: null,
        faceDetectFailMessage: '',
        bestImageData: ''
      }
    },
    created () {
      this.faceDetect = new FaceLogin({
        failCallback: this.faceDetectFailCallback,
        successCallback: this.faceDetectSuccessCallback
      })
    },
    mounted () {
      window.onresize = () => {
          var el = document.getElementById("cloudwalk-obj")
          var width = el.clientWidth||el.offsetWidth;
          var height = el.clientHeight||el.offsetHeight;
          if(this.cloudwalkObjWidth != width && width > 0 || this.cloudwalkObjHeight != height && height > 0){
              this.cloudwalkObjWidth = width;
              this.cloudwalkObjHeight = height;
              this.faceDetect.resize(width, height)
          }
        }
    },
    methods: {
      // 提交表单
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl('/authentication/json'),
              method: 'post',
              data: this.$http.adornData({
                'username': this.dataForm.userName,
                'password': this.dataForm.password,
                'uuid': this.dataForm.uuid,
                'captcha': this.dataForm.captcha
              })
            }).then(({data}) => {
                this.$cookie.set('token', data.access_token)
                this.$router.replace({ name: 'home' })
            }).catch((error) => {
               // this.getCaptcha()
              console.log(error);
              this.$message.error(error.msg)
            });
          }
        })
      },
      // 提交表单
      faceFormNext () {
        this.$refs['faceForm'].validate((valid) => {
          if (valid) {
            //左侧打开刷脸页面
            this.faceStatus = 1
            //如果获取到最佳人脸，然后调用人脸识别，获取到人脸凭证
            this.$nextTick(() => {
                 this.faceDetect.open()
            })



          }
        })
      },
      faceFormSubmit () {
       this.$http({
              url: this.$http.adornUrl('/authentication/face'),
              method: 'post',
              data: this.$http.adornData({
                'faceCredentials': this.faceLoginForm.faceCredentials,
              })
            }).then(({data}) => {
                console.info()
                this.$cookie.set('token', data.access_token)
                this.$router.replace({ name: 'home' })
            }).catch((error) => {
               // this.getCaptcha()
              this.$message.error(error.msg)
            });
      },
      // 获取验证码
      getCaptcha () {
        this.dataForm.uuid = getUUID()
        this.captchaPath = this.$http.adornUrl(`/captcha.jpg?uuid=${this.dataForm.uuid}`)
      },
      handleSwitchTab(activeName, oldActiveName) {
        if(oldActiveName == 'first' && this.faceStatus !=0){
          return false;
        }
        return true;
      },
      faceDetectFailCallback (message){
          this.faceDetectFailMessage = message
      },
      faceDetectSuccessCallback (imageData){
        //获取到最佳人脸
        this.faceStatus = 2
        this.bestImageData = "data:image/jpeg;base64," + imageData


        //识别,返回识别凭证
        this.$http({
              url: this.$http.adornUrl('/face/identify'),
              method: 'post',
              data: this.$http.adornData({
                'imageData': imageData,
                'principal': this.faceForm.principal
              })
            }).then(({data}) => {
                //识别成功
                 this.faceLoginForm.faceCredentials = data.faceCredentials
                 this.identifyUsername = data.name
                 this.faceStatus = 20
            }).catch((error) => {
              this.$message.error(error.msg)
              this.faceStatus = 21
              this.identifyResultMessage = error.msg
            });

      },
      //重置登录表单
      resetLogin (){
          this.faceStatus = 0
          this.faceForm.principal = ''
      }
    }
  }
</script>

<style lang="scss">

  .site-wrapper.site-page--login {
    height: 100%;
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-color: rgba(38, 50, 56, .6);
    overflow: hidden;
    &:before {
      position: fixed;
      top: 0;
      left: 0;
      z-index: -1;
      width: 100%;
      height: 100%;
      content: "";
      background-image: url(~@/assets/img/login_bg.jpg);
      background-size: cover;
    }
    .full{
      height: 100%;
    }
    #cloudwalk-obj{
      height: 100%;
    }

    .brand-info {
      margin: 220px 100px 0 90px;
      color: #fff;
    }
    .brand-info__text {
      margin:  0 0 22px 0;
      font-size: 48px;
      font-weight: 400;
      text-transform : uppercase;
    }
    .brand-info__intro {
      margin: 10px 0;
      font-size: 16px;
      line-height: 1.58;
      opacity: .6;
    }
    .login-main {
      padding: 150px 60px 180px;
      min-width: 400px;
      min-height: 100%;
      background-color: #fff;
    }
    .login-title {
      font-size: 16px;
    }
    .login-captcha {
      overflow: hidden;
      > img {
        width: 100%;
        cursor: pointer;
      }
    }
    .login-btn-submit {
      width: 100%;
      margin-top: 38px;
    }
    .face-small-preview {
      height: 200px
    }
  }
</style>
