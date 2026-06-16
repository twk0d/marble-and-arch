<script setup lang="ts">
import { useAuthStore } from '~/stores/auth' // just in case auto-import doesn't trigger

const authStore = useAuthStore()

const links = [
  {
    label: 'Dashboard',
    icon: 'i-heroicons-chart-pie',
    to: '/admin/dashboard'
  },
  {
    label: 'Anunciar Imóvel',
    icon: 'i-heroicons-plus-circle',
    to: '/admin/properties/create'
  },
  {
    label: 'Gestão de Leads',
    icon: 'i-heroicons-users',
    to: '/admin/leads'
  },
  {
    label: 'Aprovação de Anúncios',
    icon: 'i-heroicons-clipboard-document-check',
    to: '/admin/properties/review'
  },
  {
    label: 'Equipe e Permissões',
    icon: 'i-heroicons-user-group',
    to: '/admin/team'
  },
  {
    label: 'Configurações',
    icon: 'i-heroicons-cog-6-tooth',
    to: '/admin/settings'
  },
  {
    label: 'Suporte',
    icon: 'i-heroicons-lifebuoy',
    to: '/support'
  },
  {
    label: 'Relatórios & Analytics',
    icon: 'i-heroicons-presentation-chart-line',
    to: '/admin/analytics'
  },
  {
    label: 'Financeiro',
    icon: 'i-heroicons-banknotes',
    to: '/admin/finance'
  },
  {
    label: 'Ranking e Metas',
    icon: 'i-heroicons-trophy',
    to: '/admin/performance'
  },
  {
    label: 'Ver Site Público',
    icon: 'i-heroicons-globe-alt',
    to: '/'
  }
]

function handleLogout() {
  authStore.logout()
  navigateTo('/login')
}
</script>

<template>
  <div class="min-h-screen bg-background font-inter text-foreground flex flex-col md:flex-row">
    <!-- Sidebar Desktop -->
    <aside class="hidden md:flex flex-col w-64 border-r border-border bg-card shrink-0">
      <div class="p-6 border-b border-border">
        <NuxtLink to="/admin/dashboard" class="block">
          <h2 class="text-h3 text-primary font-playfair tracking-tight">Marble & Arch</h2>
          <p class="text-xs text-text-dimmed mt-1 tracking-widest uppercase">Admin Portal</p>
        </NuxtLink>
      </div>
      
      <div class="flex-1 p-4 overflow-y-auto space-y-2">
        <UNavigationMenu orientation="vertical" :items="links" class="w-full" />
      </div>
      
      <div class="p-4 border-t border-border">
        <UButton block color="neutral" variant="ghost" icon="i-heroicons-arrow-right-on-rectangle" @click="handleLogout" class="justify-start text-text-dimmed hover:text-error">
          Encerrar Sessão
        </UButton>
      </div>
    </aside>

    <!-- Mobile Header -->
    <header class="md:hidden flex items-center justify-between p-4 border-b border-border bg-card shrink-0">
      <h2 class="text-h4 text-primary font-playfair">M&A Admin</h2>
      <UButton color="neutral" variant="ghost" icon="i-heroicons-bars-3" />
      <!-- (Simplificado para mobile por agora) -->
    </header>

    <!-- Main Content -->
    <main class="flex-1 overflow-y-auto bg-muted/20">
      <div class="p-4 sm:p-6 lg:p-8 max-w-7xl mx-auto">
        <slot />
      </div>
    </main>
  </div>
</template>
