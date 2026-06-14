<script setup lang="ts">
const { t } = useI18n();

const columns = computed(() => [
  {
    label: 'Navegação',
    children: [
      { label: 'Home', to: '/' },
      { label: t('search.title'), to: '/search' },
      { label: 'Sobre Nós', to: '/about' }
    ]
  },
  {
    label: 'Institucional',
    children: [
      { label: 'Termos de Uso', to: '/terms' },
      { label: 'Privacidade', to: '/privacy' },
      { label: 'Contato', to: '#' }
    ]
  },
  {
    label: 'Redes Sociais',
    children: [
      { label: 'Instagram', to: 'https://instagram.com', target: '_blank', icon: 'i-simple-icons-instagram' },
      { label: 'LinkedIn', to: 'https://linkedin.com', target: '_blank', icon: 'i-simple-icons-linkedin' },
      { label: 'Facebook', to: 'https://facebook.com', target: '_blank', icon: 'i-simple-icons-facebook' }
    ]
  }
]);
</script>

<template>
  <UFooter class="bg-background border-t border-border mt-auto py-12">
    <template #top>
      <div class="grid grid-cols-1 md:grid-cols-4 gap-12 lg:gap-8 w-full max-w-[1440px] mx-auto">
        <!-- Brand / Intro -->
        <div class="col-span-1 flex flex-col gap-6">
          <div class="flex items-center gap-3">
             <UIcon name="tabler:building" class="size-8 text-primary"/>
             <span class="text-h4 text-foreground tracking-tight">Marble & Arch</span>
          </div>
          <p class="text-body-sm text-text-dimmed leading-relaxed pr-4">
            Curated discovery of high-end real estate and modern architectural masterpieces.
          </p>
        </div>
        
        <!-- Nuxt UI Pro Columns for the rest -->
        <div class="col-span-3">
          <UFooterColumns :links="columns" class="text-label-lg" />
        </div>
      </div>
    </template>

    <template #left>
      <div class="flex items-center gap-2 text-body-xs text-text-dimmed">
        &copy; {{ new Date().getFullYear() }} Marble & Arch. Todos os direitos reservados.
      </div>
    </template>

    <template #right>
      <UButton
        icon="i-heroicons-arrow-up-circle"
        color="neutral"
        variant="ghost"
        class="hover:text-primary transition-colors hover:bg-transparent"
        @click="window.scrollTo({ top: 0, behavior: 'smooth' })"
      />
    </template>
  </UFooter>
</template>

<style scoped>
</style>
