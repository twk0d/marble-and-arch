export default defineNuxtPlugin((nuxtApp) => {
  const auth = useAuthStore()

  // Intercept all $fetch calls
  const $fetch = nuxtApp.$fetch || globalThis.$fetch

  nuxtApp.provide('api', $fetch.create({
    onRequest({ options }) {
      if (auth.token) {
        options.headers = options.headers || {}
        if (Array.isArray(options.headers)) {
          options.headers.push(['Authorization', `Bearer ${auth.token}`])
        } else if (options.headers instanceof Headers) {
          options.headers.set('Authorization', `Bearer ${auth.token}`)
        } else {
          (options.headers as Record<string, string>)['Authorization'] = `Bearer ${auth.token}`
        }
      }
    }
  }))
})
