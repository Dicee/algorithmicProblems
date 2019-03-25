scrollUntilReachedBottom(printLikedVideos);

function scrollUntilReachedBottom(onScrolledToBottom) {
    window.scrollBy(0, 10000);

    const currentVideosCount = getVideoListElements().length;
    setTimeout(() => {
     const newVideosCount = getVideoListElements().length;
     if (newVideosCount > currentVideosCount) scrollUntilReachedBottom(onScrolledToBottom);
     else onScrolledToBottom();
    }, 2000)
}

function printLikedVideos() {
    const likedVideos = [];

    for (const videoListElement of getVideoListElements()) {
     const blockLink = videoListElement.children[2].children[0];
     
     const title = blockLink.children[1].children[0].children[1].innerHTML.trim();
     const link = blockLink.href;

     likedVideos.push({
      'title': title,
      'link': link
     });
    }

    console.log(JSON.stringify(likedVideos));
}

function getVideoListElements() {
    return document.getElementsByTagName("ytd-playlist-video-renderer");
}
