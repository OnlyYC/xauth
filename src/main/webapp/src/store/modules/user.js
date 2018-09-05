export default {
  namespaced: true,
  state: {
    id: 0,
    name: '',
    login: ''
  },
  mutations: {
    updateId (state, id) {
      state.id = id
    },
    updateName (state, name) {
      state.name = name
    },
    updateLogin (state, login) {
      state.login = login
    }
  }
}
