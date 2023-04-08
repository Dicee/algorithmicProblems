scrollUntilReachedBottom(printLikedVideos);

function scrollUntilReachedBottom(onScrolledToBottom) {
    const currentVideosCount = getVideoListElements().length;
    window.scrollBy(0, 10000);

    setTimeout(() => {
        const newVideosCount = getVideoListElements().length;
        if (newVideosCount > currentVideosCount) scrollUntilReachedBottom(onScrolledToBottom);
        else onScrolledToBottom();
    }, 4000)
}

function printLikedVideos() {
    const likedVideos = [];
    let count = 1;

    for (const link of getVideoLinkElements()) {
        likedVideos.push({
            'order': count,
            'title': link.title,
            'link': link.href
        });
        count++
    }

    console.log(JSON.stringify(likedVideos));
}

function printLikedVideos_old() {
    const likedVideos = [];

    for (const videoListElement of getVideoListElements()) {
        const blockLink = videoListElement.children[1].children[0];

        const title = blockLink.children[1].children[0].children[1].innerHTML.trim();
        const link = blockLink.href;

        likedVideos.push({
            'title': title,
            'link': link
        });
    }

    console.log(JSON.stringify(likedVideos));
}

function getVideoLinkElements() {
    return document.querySelectorAll("a#video-title");
}

function getVideoListElements() {
    return document.getElementsByTagName("ytd-playlist-video-renderer");
}
