import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/views/Home";
import Archive from "@/views/Archive";
import Category from "@/views/Category";
import About from "@/views/About";
import Link from "@/views/Link";

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
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
