import {
  ElAside,
  ElButton, ElCard, ElCollapseTransition,
  ElContainer,
  ElFooter,
  ElMain,
  ElMenu, ElMenuItem,
  ElRadioButton,
  ElRadioGroup,
  ElSubmenu
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
}
