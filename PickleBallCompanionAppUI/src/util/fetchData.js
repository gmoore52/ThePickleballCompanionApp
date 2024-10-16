// const baseUrl = '.' // production base URL
const baseUrl = 'http://localhost:8080/pickleball-companion' // development base URL

// const json = await fetchData(url, {                   ** SAMPLE CALL **
//     method: 'POST', // (or 'GET')
//     body: JSON.stringify(*data that you wanna send*),
//     credentials: 'include', // This is all you need for a GET
//     headers: {
//         'Content-type':'application/json'
//     }
// });

const fetchData = async function (url, options = {}) {
    const URL = `${baseUrl}${url}`
    const response = await fetch(URL, options)
    if (!response.ok) {
        throw new Error(JSON.stringify({message: response.statusText, status: response.status}))
    } else {
        if (response.status === 204) {
            return
        }
    }
    if (response.headers.get('content-type').startsWith('text/plain')) {
        return await response.text()
    } else {
        return await response.json()
    }
}

export { fetchData }
