import {
  ElAside, ElAvatar,
  ElButton, ElCard, ElCollapseTransition,
  ElContainer,
  ElFooter, ElInfiniteScroll, ElInput, ElLink, ElLoading,
  ElMain,
  ElMenu, ElMenuItem, ElMessage, ElPagination,
  ElRadioButton,
  ElRadioGroup, ElRow,
  ElSubmenu, ElTabPane, ElTabs, ElTag, ElTimeline, ElTimelineItem, ElTooltip, ElTree
} from 'element-plus'
import lang from 'element-plus/lib/locale/lang/zh-cn'
import locale from 'element-plus/lib/locale'

export default (app) => {
  locale.use(lang)
  app.use(ElButton)
  app.use(ElContainer)
  app.use(ElAside)
  app.use(ElFooter)
  app.use(ElMain)
  app.use(ElMenu)
  app.use(ElRadioGroup)
  app.use(ElRadioButton)
  app.use(ElSubmenu)
  app.use(ElMenuItem)
  app.use(ElCard)
  app.use(ElCollapseTransition)
  app.use(ElAvatar)
  app.use(ElInfiniteScroll)
  app.use(ElTag)
  app.use(ElPagination)
  app.use(ElLoading)
  app.use(ElLink)
  app.use(ElMessage)
  app.use(ElTree)
  app.use(ElTimeline)
  app.use(ElTimelineItem)
  app.use(ElTooltip)
  app.use(ElTabs)
  app.use(ElTabPane)
  app.use(ElInput)
}
