import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios';
import './index.css';

axios.defaults.baseURL = 'http://localhost:8080';

const app = createApp(App)

app.config.globalProperties.$axios = axios

app.use(router)

app.mount('#app')
