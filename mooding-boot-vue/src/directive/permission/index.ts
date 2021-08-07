import hasPermi from './hasPermi'

const install = function(Vue:any) {
    // Vue.directive('hasRole', hasRole)
    Vue.directive('hasPermi', hasPermi)
  }
  
//   if (window.Vue:Any) {
//     // window['hasRole'] = hasRole
//     window['hasPermi'] = hasPermi
//     Vue.use(install); // eslint-disable-line
//   }
  
  export default install