export default defineNuxtRouteMiddleware((to) => {
    const auth = useAuthStore()

    if (!auth.isAuthenticated && to.path.startsWith('/admin')) {
        return navigateTo('/login')
    }
})
