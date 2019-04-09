
function setStorage(item, val) {
  window.localStorage.setItem(item,val);
}
// function setStorageStr(item, val) {
//   window.localStorage.setItem(item,JSON.stringify(val));
// }
export default {
    namespaced: true,
    state: {
      themecolor: localStorage.getItem('themecolor') || 'blue',
      wrapperCenter: localStorage.getItem('wrapperCenter') === 'true' ? true : false  || false,
      headerSkin: localStorage.getItem('headerSkin') === 'true' ? true : false  || false,
      asideTop: localStorage.getItem('asideTop') === 'true' ? true : false  || false,
      asideSkin: localStorage.getItem('asideSkin') === 'true' ? true : false  || false,
      mainType: localStorage.getItem('mainType') === 'true' ? true : false  || false,
      mode: localStorage.getItem('mode') === 'horizontal' ? 'horizontal' : 'vertical'  || 'vertical',
      mainTabs:[],
      mainTabsActiveName:''
    },
    mutations: {
      //更新主题颜色
      SET_themecolor(state,curcolor){
        state.themecolor = curcolor;
        setStorage('themecolor', curcolor);
      },
      SET_wrapperCenter(state, val){
        state.wrapperCenter = val;
        setStorage('wrapperCenter', val);
      },
      SET_headerSkin(state, val){
        state.headerSkin = val;
        setStorage('headerSkin', val);
      },
      SET_asideTop(state, val){
        state.asideTop = val;
        setStorage('asideTop', val);
      },
      SET_asideSkin(state, val){
        state.asideSkin = val;
        setStorage('asideSkin', val);
      },
      SET_mainType(state, val){
        state.mainType = val;
        setStorage('mainType', val);
      },
      SET_mode(state, val){
        state.mode = val;
        setStorage('mode', val);
      },
      SET_mainTabs(state, val){
        state.mainTabs = val;
      },
      SET_mainTabsActiveName(state, val){
        state.mainTabsActiveName = val;
      },
    }
  };
  