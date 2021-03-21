function sendRequestGet(method, url) {
    const headers = {
        'Content-Type': 'application/json'
    }

    return  fetch(url, {
        method: method,
        headers: headers
    }).then(response => {
        if (response.ok) {
                return response.json()
        }

        return response.json().then(error => {
            const e = new Error('Что-то пошло не так')
            e.data = error
            throw e
        })
    })
}

function sendRequestPost(method, url, body = null) {
    const headers = {
        'Content-Type': 'application/json'
    }

    return  fetch(url, {
        method: method,
        body: JSON.stringify(body),
        headers: headers
    }).then(response => {
        if (response.ok) {
            return response.json()
        }

        return response.json().then(error => {
            const e = new Error('Что-то пошло не так')
            e.data = error
            throw e
        })
    })
}

function sendRequestDelete(method, url, body = null) {
    const headers = {
        'Content-Type': 'application/json'
    }

    return  fetch(url, {
        method: method,
        body: JSON.stringify(body),
        headers: headers
    }).then(response => {
        if (response.ok) {
            return response
        }

        return response.then(error => {
            const e = new Error('Что-то пошло не так')
            e.data = error
            throw e
        })
    })
}
