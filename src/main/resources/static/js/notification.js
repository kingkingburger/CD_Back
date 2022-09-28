const eventSource = new EventSource(`/subscribe?id=1`);

eventSource.onopen = (e) => {
    console.log(e);
};

eventSource.onerror = (e) => {
    console.log(e);
};
eventSource.onmessage = (e) => {
    console.log(e.data);
};
