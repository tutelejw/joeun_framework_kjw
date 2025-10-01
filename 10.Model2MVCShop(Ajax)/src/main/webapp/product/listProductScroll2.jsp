success: function (data) {
    var $items = $(data).find("table tr");

    if ($items.length > 0) {
        $("table").append($items);

        // 이전 감시자 해제
        loadMoreObserver.disconnect();

        // 새로운 마지막 row 감시 등록
        const newLastProductElement = $(".ct_list_pop:last");
        if (newLastProductElement.length > 0) {
            loadMoreObserver.observe(newLastProductElement[0]);
        }

        loading = false;

        if ($items.length < 1) {
            noMoreData = true;
        }
    } else {
        noMoreData = true;
    }
    $("#loadingMessage").hide();
}
