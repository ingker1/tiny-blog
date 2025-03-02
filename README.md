# Tiny-Blog
一个自用的的博客系统，基于 SpringBoot / Vue.js / MySQL / Mybatis 开发。

轻量设计，高度定制，使用精简的四张表完成一个动态博客的所有核心功能。

无过多开发，复杂度在动态博客和静态博客之间，既拥有动态博客的易用性和灵活性，又不像其他博客系统过于臃肿。
## 开发进度
### 前台显示
- [x] 博客列表
    - [x] 分页显示
    - [x] 显示标题摘要
    - [x] 显示点赞评论浏览数
    - [x] 摘要图片
- [ ] 文章显示
    - [x] 代码高亮框
    - [x] 图片浏览器
    - [ ] 评论功能
        - [x] 楼中楼
        - [x] 评论回复
        - [x] 楼层显示
        - [x] 楼层跳转
        - [ ] 评论头像
        - [ ] OAuth2 三方登录
    - [ ] 文章合集
- [ ] 标签聚合
    - [ ] 分类归档显示
    - [ ] 标签归档显示
    - [ ] 归档数可视化
- [ ] 美化
    - [ ] 半透明背景
    - [ ] 随机背景图
    - [ ] 季节昼夜变化首页图
### 后台管理
- [x] 编辑器（[Vditor](https://github.com/Vanessa219/vditor)）
    - [x] MarkDown模式 （分屏渲染、即时渲染）
    - [x] 富文本模式
    - [x] 目录大纲跳转
    - [x] 代码高亮
    - [x] 文本转义
- [ ] 文章管理
    - [x] 快捷编辑
    - [ ] 文章多选
    - [ ] 定时发布
    - [x] 自定义分页
        - [x] 手动选择每页条数
        - [x] 分页跳转
    - [x] 搜索功能
    - [x] 条件筛选（状态、分类、排序）
    - [x] 回收站
- [x] 分类管理
- [x] 评论管理
- [x] 登录验证

### 用户体验和建站优化
- [ ] 加载动画
- [ ] 站点地图
- [ ] RSS订阅
- [ ] SEO优化
