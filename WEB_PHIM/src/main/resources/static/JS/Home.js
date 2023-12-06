$(document).ready(() => {
    $('#hamburger-menu').click(() => {
        $('#hamburger-menu').toggleClass('active')
        $('#nav-menu').toggleClass('active')
    })
});
let navText = ["<i class='bx bx-chevron-left'></i>", "<i class='bx bx-chevron-right'></i>"]

$('#hero-carousel').owlCarousel({
    items: 1,
    dots: false,
    loop: true,
    nav:true,
    navText: navText,
    autoplay: true,
    autoplayHoverPause: true
})

// Initialize Swiper
var swiper = new Swiper(".mySwiper", {
    slidesPerView: 2,
    slidesPerGroup: 2,
    loop:true,
    grabCursor: true,
    keyboard: {
        enabled: true,
    },
    breakpoints: {
        769: {
            slidesPerView: 5,
            slidesPerGroup: 5,
            spaceBetween: 0,
        },
    },
    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    },
});

// new DataTable('#example', {
//     scrollX: true
// });

$(function () {
    $("body").on('hidden.bs.modal', function (e) {
        var $iframes = $(e.target).find("iframe");
        $iframes.each(function (index, iframe) {
            $(iframe).attr("src", $(iframe).attr("src"));
        });
    });
});