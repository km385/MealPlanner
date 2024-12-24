import VueCookies from 'vue-cookies'



export function useCookies() {
    const setCookie = (name, value, expiresIn) => {
        // Convert milliseconds to days (86400000 ms = 1 day)
        const days = Math.floor(expiresIn / 86400000)
        VueCookies.set(name, value, `${days}d`)
    }

    const getCookie = (name) => {
        return VueCookies.get(name)
    }

    const removeCookie = (name) => {
        VueCookies.remove(name)
    }

    return {
        setCookie,
        getCookie,
        removeCookie
    }
}