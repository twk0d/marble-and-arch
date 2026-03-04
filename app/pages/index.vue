<script setup lang="ts">
const { t } = useI18n()
</script>

<template>
  <div>
    <UMain>
      <!-- Hero Section -->
      <UPageHero
        :title="t('header.title')"
        :description="t('styleguide.hero.description')"
        align="center"
        class="bg-neutral-50 dark:bg-neutral-900/50"
      >
        <template #links>
          <div class="flex justify-center gap-4">
            <UButton to="/search" size="xl" icon="i-heroicons-magnifying-glass">
              Encontrar Imóveis
            </UButton>
            <UButton to="/admin/properties/create" size="xl" variant="outline" color="neutral">
              Anunciar Imóvel
            </UButton>
          </div>
        </template>
      </UPageHero>

      <!-- Categories Section -->
      <UPageSection
        title="Explore por Categorias"
        description="Encontre o imóvel ideal para o seu estilo de vida"
        class="py-16"
      >
        <UPageGrid>
          <UPageCard
            v-for="type in ['HOUSE', 'CONDOMINIUM_HOUSE', 'PENTHOUSE', 'STUDIO', 'APARTMENT', 'TOWNHOUSE']"
            :key="type"
            :title="t(`propertyType.${type}`)"
            :to="`/search?type=${type}`"
            class="text-center group hover:border-primary transition-colors"
          >
            <template #icon>
              <UIcon
                name="i-heroicons-home"
                class="w-10 h-10 mx-auto text-primary group-hover:scale-110 transition-transform"
              />
            </template>
          </UPageCard>
        </UPageGrid>
      </UPageSection>

      <!-- Featured Luxury Properties Placeholder -->
      <UPageSection
        title="Imóveis de Luxo em Destaque"
        description="Uma seleção exclusiva das melhores propriedades em nosso catálogo"
        variant="subtle"
        class="py-16"
      >
        <UPageGrid>
          <UPageCard
            v-for="i in 3"
            :key="i"
            title="Mansão Contemporânea"
            description="Localização Privilegiada - São Paulo, SP"
            class="overflow-hidden"
          >
            <template #header>
              <div class="aspect-video bg-neutral-200 dark:bg-neutral-800 flex items-center justify-center text-4xl opacity-30">
                🏠
              </div>
            </template>
            <template #footer>
              <div class="flex justify-between items-center">
                <span class="text-primary font-bold">R$ 5.500.000,00</span>
                <UButton variant="ghost" icon="i-heroicons-arrow-right" to="/search" />
              </div>
            </template>
          </UPageCard>
        </UPageGrid>
        
        <template #bottom>
          <div class="flex justify-center mt-12">
            <UButton to="/search" variant="link" size="lg" icon="i-heroicons-plus">
              Ver Catálogo Completo
            </UButton>
          </div>
        </template>
      </UPageSection>

      <!-- Advantages Section -->
      <UPageSection
        title="Vantagens de Anunciar na Keystone"
        description="Por que somos a escolha número um para vender seu imóvel de alto padrão"
        class="py-24"
      >
        <UPageGrid :columns="3">
          <UPageFeature
            title="Visibilidade Premium"
            description="Seu imóvel exposto para uma audiência qualificada e interessada em alto padrão."
            icon="i-heroicons-eye"
          />
          <UPageFeature
            title="Marketing Digital"
            description="Estratégias avançadas de tráfego pago e redes sociais para acelerar a venda."
            icon="i-heroicons-presentation-chart-line"
          />
          <UPageFeature
            title="Suporte Especializado"
            description="Consultores prontos para auxiliar em todas as etapas burocráticas e jurídicas."
            icon="i-heroicons-user-group"
          />
          <UPageFeature
            title="Fotografia Profissional"
            description="Destaque os melhores ângulos da sua propriedade com produção de alta qualidade."
            icon="i-heroicons-camera"
          />
          <UPageFeature
            title="Relatórios de Performance"
            description="Acompanhe em tempo real o interesse e as visitas agendadas para o seu anúncio."
            icon="i-heroicons-document-chart-bar"
          />
          <UPageFeature
            title="Segurança e Sigilo"
            description="Garantimos a total privacidade e segurança em todas as transações realizadas."
            icon="i-heroicons-shield-check"
          />
        </UPageGrid>
      </UPageSection>

      <!-- CTA Section -->
      <UPageSection class="py-16">
        <UPageCard
          title="Pronto para o próximo passo?"
          description="Seja para comprar ou vender, a Keystone é sua parceira ideal."
          class="bg-primary text-white text-center"
          :ui="{ title: 'text-white text-h2', description: 'text-white/80 text-lg' }"
        >
          <template #footer>
            <div class="flex justify-center gap-4">
              <UButton size="xl" color="neutral" variant="solid" to="/signup">
                Começar Agora
              </UButton>
              <UButton size="xl" color="white" variant="outline" to="/contact">
                Falar com Consultor
              </UButton>
            </div>
          </template>
        </UPageCard>
      </UPageSection>
    </UMain>
  </div>
</template>
