<script setup lang="ts">
const props = withDefaults(defineProps<{
  amount: number;
  currency?: string;
  locale?: string;
  size?: 'xs' | 'sm' | 'md' | 'lg' | 'xl' | '2xl' | '3xl';
  color?: string;
}>(), {
  currency: 'R$',
  locale: 'pt-BR',
  size: 'md',
  color: 'primary'
});

const formattedAmount = computed(() => {
  return props.amount.toLocaleString(props.locale, {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  });
});

const sizeClasses = {
  'xs': 'text-body-xs',
  'sm': 'text-body-sm',
  'md': 'text-body-base',
  'lg': 'text-h5',
  'xl': 'text-h4',
  '2xl': 'text-h3',
  '3xl': 'text-h2'
};
</script>

<template>
  <span :class="[sizeClasses[size], `text-${color}`]" class="font-inter">
    {{ currency }} {{ formattedAmount }}
  </span>
</template>
