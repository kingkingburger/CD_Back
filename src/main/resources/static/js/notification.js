//
// const memberSession = [[${session.loginMember}]];
//
// console.log(memberSession);
//
// if (memberSession != null) {
//     const eventSource = new EventSource(`/subscribe`);
//
//     eventSource.onopen = (e) => {
//         console.log(e);
//     };
//
//     eventSource.onerror = (e) => {
//         console.log(e);
//     };
//     eventSource.onmessage = (e) => {
//         console.log(e.data);
//     };
//
// }
