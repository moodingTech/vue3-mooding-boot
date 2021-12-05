import vue from '@vitejs/plugin-vue';
import { resolve } from 'path';
import type { UserConfig } from 'vite';
import { loadEnv } from './src/utils/viteBuild';


import viteSvgIcons from 'vite-plugin-svg-icons';


const { VITE_PORT, VITE_OPEN, VITE_PUBLIC_PATH } = loadEnv();
import path from 'path';// ts如果报错 npm i @types/node -D

const pathResolve = (dir: string): any => {
    return resolve(__dirname, '.', dir);
};

const alias: Record<string, string> = {
    '@': pathResolve('src/'),
};


const viteConfig: UserConfig = {    
    plugins: [vue(), viteSvgIcons({
        // 配置路劲在你的src里的svg存放文件
        iconDirs: [path.resolve(process.cwd(), 'src/assets/icons')],
        symbolId: 'icon-[dir]-[name]',
    })],
    
    root: process.cwd(),
    resolve: { alias },
    // base: process.env.NODE_ENV === 'production' ? VITE_PUBLIC_PATH : './',
    base: '/',
    optimizeDeps: {
        include: ['element-plus/lib/locale/lang/zh-cn', 'element-plus/lib/locale/lang/en', 'element-plus/lib/locale/lang/zh-tw'],
    },
    server: {
        host: '0.0.0.0',
        port: VITE_PORT,
        open: VITE_OPEN,
        proxy: {
            '/api': {
                target: 'http://49.235.52.198:8081/api',
                // target: 'http://127.0.0.1:8081/api',
                ws: true,
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, ''),
            },
            '/test': {
                // target: 'http://49.235.52.198:8081/api',
                target: 'https://view.inews.qq.com',
                ws: true,
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/test/, ''),
            },
            '/gitee': {
                target: 'https://gitee.com',
                ws: true,
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/gitee/, ''),
            },
        },
    },
    build: {
        outDir: 'dist',
        minify: 'esbuild',
        sourcemap: false,
    },
    // define: {
    //     __VUE_I18N_LEGACY_API__: JSON.stringify(false),
    //     __VUE_I18N_FULL_INSTALL__: JSON.stringify(false),
    //     __INTLIFY_PROD_DEVTOOLS__: JSON.stringify(false),
    // },
};

export default viteConfig;
// https://vitejs.dev/config/
// export default defineConfig({
//     // 这里已经将src/icons/svg/下的svg全部导入，无需再单独导入
//     plugins: [vue(), viteSvgIcons({
//         // 配置路劲在你的src里的svg存放文件
//         iconDirs: [path.resolve(process.cwd(), 'src/assets/icons')],
//         symbolId: 'icon-[dir]-[name]',
//     })],
//     resolve: { alias },
//     // base: process.env.NODE_ENV === 'production' ? VITE_PUBLIC_PATH : './',
//     base: './',
//     server: {
//         port: VITE_PORT,
//         open: VITE_OPEN,
//         proxy: {
//             '/api': {
//                 target: 'http://49.235.52.198:8081/',
//                 ws: true,
//                 changeOrigin: true,
//                 rewrite: (path) => path.replace(/^\/api/, ''),
//             },
//         },
//     },
//     build: {
//         outDir: 'dist',
//         minify: 'esbuild',
//         sourcemap: false,
//         // base: './',
//     },
//     // hostname: '0.0.0.0',
//     // port: 8090,
//     // 是否自动在浏览器打开
//     // open: true,
//     // 是否开启 https
//     // https: false,
//     // 服务端渲染
//     // ssr: false,
//     /**
//      * 在生产中服务时的基本公共路径。
//      * @ default '/'
//      */
//     // base: './',
// })
