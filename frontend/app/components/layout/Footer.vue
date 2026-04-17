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
      { label: 'Contato', to: '/contact' }
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
  <UFooter>
    <template #top>
      <UFooterColumns :links="columns" />
    </template>

    <template #left>
      <div class="flex items-center gap-2">
        <span class="text-primary font-bold text-lg">Marble & Arch</span>
        <span class="text-body-muted text-sm border-l border-border pl-2">
          &copy; {{ new Date().getFullYear() }} - Todos os direitos reservados
        </span>
      </div>
    </template>

    <template #right>
      <UButton
        icon="i-heroicons-arrow-up-circle"
        color="neutral"
        variant="ghost"
        @click="window.scrollTo({ top: 0, behavior: 'smooth' })"
      />
    </template>
  </UFooter>
</template>
