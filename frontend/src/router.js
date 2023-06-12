import {createRouter, createWebHistory} from 'vue-router';
import UserPage from './components/UserPage';
import AdminPage from './components/AdminPage';
import LoginPage from './components/LoginPage';

const routes = [
    {path:'/user', component:UserPage}, //root 경로로 들어올때는 component를 home으로 연결해라
    {path:'/admin', component:AdminPage},
    {path:'/login', component:LoginPage}
]

const router = createRouter({
    history : createWebHistory(),
    routes
})

export default router;