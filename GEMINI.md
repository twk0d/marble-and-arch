# Nuxt 3 Frontend - Keystone Project

Este diretório contém o front-end da aplicação Keystone, desenvolvido com Nuxt 3.

## 🛠 Tecnologias & Convenções

- **Framework:** Nuxt 3 (Vue 3)
- **Componentes:** [Nuxt UI Pro](https://ui3.nuxt.com/) (Prioridade Absoluta)
- **Estado:** Pinia
- **Internacionalização:** @nuxtjs/i18n
- **Estilização:** Vanilla CSS / Tailwind (através do Nuxt UI)

## 🎨 Biblioteca de Componentes (Nuxt UI Pro)

### Mandatos de Desenvolvimento
1.  **Prioridade Absoluta:** Sempre utilize os componentes do Nuxt UI Pro. Não crie componentes customizados ou use tags HTML puras se existir um componente correspondente na biblioteca.
2.  **Styleguide como Fonte de Verdade:** O arquivo `app/pages/styleguide.vue` (visualizável em `/styleguide`) é a referência definitiva para Tipografia, Cores, Bordas, Sombras e padrões de UI. Toda criação ou refatoração deve ser validada contra os padrões documentados ali.
3.  **Consulta às Docs:** Sempre consulte a documentação local em `../Nuxt UI Pro v4.8.2 docs/` antes de implementar qualquer interface.
4.  **Refatoração:** Ao editar arquivos existentes, refatore elementos HTML ou componentes customizados para usar os equivalentes do Nuxt UI Pro e seguir os padrões do Styleguide.
5.  **Consistência Reativa:** Utilize os utilitários de CSS centralizados (`main.css`) e chaves de tradução (`i18n`) conforme demonstrado no Styleguide para garantir que o sistema seja visualmente coeso e multi-idioma.
6.  **Confirmação:** Se for estritamente necessário NÃO usar um componente da biblioteca ou desviar do Styleguide, você **DEVE** perguntar ao usuário e justificar a decisão antes de prosseguir.

### Documentação Local
A documentação completa do Nuxt UI Pro v4.8.2 está disponível em:
`C:\Users\twkod\Documents\Mable and Arch\Nuxt UI Pro v4.8.2 docs`

### Lista de Componentes Disponíveis (v4.8.2):
Abaixo estão os componentes disponíveis na biblioteca. Use o prefixo `U` (ex: `UButton`, `UModal`).

- **Layout & Structure:** `App`, `Container`, `Main`, `Header`, `Footer`, `FooterColumns`, `Sidebar`
- **Navigation:** `Breadcrumb`, `ContentNavigation`, `ContentToc`, `ContextMenu`, `DropdownMenu`, `Link`, `NavigationMenu`, `Pagination`, `Tabs`, `Stepper`, `Tree`
- **Dashboard:** `DashboardGroup`, `DashboardNavbar`, `DashboardPanel`, `DashboardResizeHandle`, `DashboardSearch`, `DashboardSearchButton`, `DashboardSidebar`, `DashboardSidebarCollapse`, `DashboardSidebarToggle`, `DashboardToolbar`
- **Page Building:** `Page`, `PageAnchors`, `PageAside`, `PageBody`, `PageCard`, `PageColumns`, `PageCta`, `PageFeature`, `PageGrid`, `PageHeader`, `PageHero`, `PageLinks`, `PageList`, `PageLogos`, `PageSection`
- **Forms & Input:** `AuthForm`, `Button`, `Checkbox`, `CheckboxGroup`, `ColorPicker`, `FileUpload`, `Form`, `FormField`, `FieldGroup`, `Input`, `InputDate`, `InputMenu`, `InputNumber`, `InputTags`, `InputTime`, `Listbox`, `PinInput`, `RadioGroup`, `Select`, `SelectMenu`, `Slider`, `Switch`, `Textarea`
- **Data & Display:** `Accordion`, `Alert`, `Avatar`, `AvatarGroup`, `Badge`, `Banner`, `BlogPost`, `BlogPosts`, `Calendar`, `Card`, `Carousel`, `Chip`, `Collapsible`, `Empty`, `Icon`, `Kbd`, `Marquee`, `Progress`, `Separator`, `Skeleton`, `Table`, `Timeline`, `User`
- **Overlays:** `Drawer`, `Modal`, `Popover`, `Slideover`, `Tooltip`, `Toast`
- **Editor & Chat:** `Editor`, `EditorDragHandle`, `EditorEmojiMenu`, `EditorMentionMenu`, `EditorSuggestionMenu`, `EditorToolbar`, `Chat`, `ChatMessage`, `ChatMessages`, `ChatPalette`, `ChatPrompt`, `ChatPromptSubmit`, `ChatReasoning`, `ChatShimmer`, `ChatTool`
- **Utility & Misc:** `ChangelogVersion`, `ChangelogVersions`, `ColorModeAvatar`, `ColorModeButton`, `ColorModeImage`, `ColorModeSelect`, `ColorModeSwitch`, `ContentSearch`, `ContentSearchButton`, `ContentSurround`, `Error`, `LocaleSelect`, `PricingPlan`, `PricingPlans`, `PricingTable`, `ScrollArea`, `Theme`

## 🔗 Integração com API

As chamadas para a API devem respeitar o proxy configurado no `nuxt.config.ts`.
Base URL local: `http://localhost:3000/api/v1` (redireciona para `:8081`)

Consulte o `FRONTEND_INTEGRATION_GUIDE.md` na raiz para detalhes de contratos e endpoints.
