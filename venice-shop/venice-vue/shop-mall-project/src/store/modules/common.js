
export default {
  namespaced: true,
  state: {
    sideMenu: [],
    sideMenuCollapse: false,
    treeMenu: [],
    mainLoadNum: 0,
    breadCrumbsTitles: [],
    breadCrumbsMap: {},
    ueId: 0
  },
  mutations: {
    updateSideMenu(state, menu) {
      state.sideMenu = menu;
    },
    updateSideMenuCollapse(state, val) {
      state.sideMenuCollapse = val;
    },
    updateTreeMenu(state, menu) {
      state.treeMenu = menu;
    },
    updateMainLoadNum(state, val) {
      state.mainLoadNum = val;
    },
    updateBreadCrumbsTitles(state, val) {
      state.breadCrumbsTitles = val;
    },
    updateBreadCrumbsMap(state, val) {
      state.breadCrumbsMap = val;
    },
    updateUeId (state, id) {
      state.ueId = id
    }
  }
};
