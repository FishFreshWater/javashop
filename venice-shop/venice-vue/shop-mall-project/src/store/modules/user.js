export default {
  namespaced: true,
  state: {
    id: 0,
    name: "",
    sideMessageShow: false,
    sideControlShow: false,
    newMessage: false
  },
  mutations: {
    updateId(state, id) {
      state.id = id;
    },
    updateName(state, name) {
      state.name = name;
    },
    updateSideMessageShow(state, val) {
      state.sideMessageShow = val;
    },
    updateSideControlShow(state, val) {
      state.sideControlShow = val
    },
    updateNewMessage(state, val) {
      state.newMessage = val;
    }
  }
};
