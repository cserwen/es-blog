import {
  ElAside, ElAvatar,
  ElButton, ElCard, ElCollapseTransition,
  ElContainer,
  ElFooter, ElInfiniteScroll,
  ElMain,
  ElMenu, ElMenuItem,
  ElRadioButton,
  ElRadioGroup, ElRow,
  ElSubmenu, ElTag
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
}
