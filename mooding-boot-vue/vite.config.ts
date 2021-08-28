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
    '@': pathResolve('/src/'),
};


const viteConfig: UserConfig = {
    plugins: [vue(), viteSvgIcons({
        // 配置路劲在你的src里的svg存放文件
        iconDirs: [path.resolve(process.cwd(), 'src/assets/icons')],
        symbolId: 'icon-[dir]-[name]',
    })],
    root: process.cwd(),
    resolve: { alias },
    base: '/',
    server: {
        host: '0.0.0.0',
        port: VITE_PORT,
        open: VITE_OPEN,
        proxy: {
            '/api': {
                target: 'http://127.0.0.1:8081/api',
                ws: true,
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, ''),
            },
            '/test': {
                target: 'https://view.inews.qq.com',
                ws: true,
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/test/, ''),
            },
        },
    },
    build: {
        outDir: 'dist',
        minify: 'esbuild',
        sourcemap: false,
    },
};

export default viteConfig;

