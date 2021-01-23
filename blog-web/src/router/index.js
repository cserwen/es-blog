import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/views/Home";
import Archive from "@/views/Archive";
import Category from "@/views/Category";
import About from "@/views/About";
import Link from "@/views/Link";
import Article from "@/views/Article";

const routes = [
  {
    path:'/',
    name: 'Home',
    component: Home
  },
  {
    path:'/archives',
    name: 'Archive',
    component: Archive
  },
  {
    path: "/categories",
    name: "Category",
    component: Category
  },
  {
    path: "/about",
    name: "About",
    component: About
  },
  {
    path: "/links",
    name: "Link",
    component: Link
  },
  {
    path: "/article/:id",
    name: "Article",
    component: Article
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
