import {createApp} from 'vue'
import App from './App.vue'
import {store, key} from './store'
import router from './router'
import mitt from 'mitt'
import ElementPlus from 'element-plus'
<<<<<<< HEAD
import 'element-plus/lib/theme-chalk/index.css'
=======
import 'element-plus/dist/index.css'
import locale from "element-plus/lib/locale/lang/zh-cn";//需要新加的代码
>>>>>>> master
import '@/assets/style/index.scss'
import './permission' // 路由守卫
import {addDateRange,parseTime,handleTree,selectDictLabel,download} from '@/utils'

import { getDicts } from "@/api/system/dict/data"
import hasPermi from './directive/permission/hasPermi'

//引入svg标签
// import 'vite-plugin-svg-icons/register'

// 自定义表格工具扩展
import RightToolbar from "@/components/RightToolbar/index.vue"
import Pagination from "@/components/Pagination/index.vue"
import SvgIcon from '@/components/SvgIcon/index.vue' // 全局svg图标组件
import MDInput from '@/components/mooding/MDInput.vue';
<<<<<<< HEAD
 
// createApp(App).mount('#app')
const app = createApp(App);

app.use(router).use(store, key).use(ElementPlus).mount('#app');
=======

// createApp(App).mount('#app')
const app = createApp(App);

app.use(router).use(store, key).use(ElementPlus, {locale}).mount('#app');
>>>>>>> master
//全局加载自定义工具插件
app.component('RightToolbar', RightToolbar)
app.component('Pagination', Pagination)
app.component('svg-icon', SvgIcon)
app.component('md-input', MDInput)

// 自定义标签 v-hasPermi
app.directive("hasPermi",hasPermi)

// 全局方法挂载
app.config.globalProperties.getDicts = getDicts
app.config.globalProperties.mittBus = mitt();
app.config.globalProperties.addDateRange = addDateRange;
app.config.globalProperties.parseTime = parseTime
app.config.globalProperties.handleTree = handleTree
app.config.globalProperties.selectDictLabel = selectDictLabel
app.config.globalProperties.download = download


