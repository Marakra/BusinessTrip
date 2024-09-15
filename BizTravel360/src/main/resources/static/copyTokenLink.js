function copyTokenLink(button) {
    const url = `${window.location.origin}/sending-password/${button.dataset.token}`;

    //Copy URL
    navigator.clipboard.writeText(url).then(() => {
        alert('Link copied to clipboard: ' + url);
    }).catch(err => {
        console.error('Failed to copy link: ' + url);
    });
}