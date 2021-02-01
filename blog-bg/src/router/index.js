import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/views/Home";
import Login from "@/views/Login";
import Editor from "@/views/Editor";
import Blog from "@/views/Blog";
import Comment from "@/views/Comment";
import Editor2 from "@/views/Editor2";

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    children: [
      {
        path: '/edit',
        name: 'Editor',
        component: Editor
      },
      {
        path: '/edit2',
        name: 'Editor2',
        component: Editor2
      },
      {
        path: '/blog',
        name: 'Blog',
        component: Blog
      },
      {
        path: '/comment',
        name: 'Comment',
        component: Comment
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
