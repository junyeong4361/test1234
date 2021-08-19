class Ajax {
    static request(params, callback, fallback) {
        const url = params['url'];
        const method = typeof (params['method']) !== 'string' || (
            params['method'] !== Ajax.METHOD_GET &&
            params['method'] !== Ajax.METHOD_POST) ? Ajax.METHOD_POST : params['method'];
        const data = params['data'];
        const xhr = new XMLHttpRequest();
        xhr.open(method, url);
        xhr.onreadystatechange = () => {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status >= 200 && xhr.status < 300) {
                    if (typeof(callback) === 'function') {
                        callback(xhr.status, xhr.responseText);
                    }
                } else {
                    if (typeof(fallback) === 'function') {
                        fallback(xhr.status);
                    }
                }
            }
        };
        if (data === undefined) {
            xhr.send();
        } else {
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.send(JSON.stringify(data));
        }
    }
}

Ajax.METHOD_GET = 'GET';
Ajax.METHOD_POST = 'POST';