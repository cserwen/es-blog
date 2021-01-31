import {
  ElButton,
  ElCol,
  ElInput,
  ElLoading,
  ElMenu,
  ElMenuItem,
  ElMessage,
  ElRow,
  ElTabPane,
  ElTabs
} from 'element-plus'
import lang from 'element-plus/lib/locale/lang/zh-cn'
import locale from 'element-plus/lib/locale'

export default (app) => {
  locale.use(lang)
  app.use(ElButton)
  app.use(ElMessage)
  app.use(ElInput)
  app.use(ElTabs)
  app.use(ElTabPane)
  app.use(ElMenu)
  app.use(ElMenuItem)
  app.use(ElRow)
  app.use(ElCol)
  app.use(ElLoading)
}
