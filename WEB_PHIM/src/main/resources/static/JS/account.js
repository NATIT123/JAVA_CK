
const render = async (items, listId, listName) => {
    const itemMovies = await Promise.all(
        items.map(async (id) => {
            const res = await fetch(
                `${api.base_url}${id}?` +
                new URLSearchParams({
                    api_key: api.api_key,
                }) +
                api.language
            );
            const data = await res.json();
            return data;
        })
    );

    itemMovies.forEach((movie) => {
        makeCard(movie, listId, listName);
    });

    const removeButton = document.createElement("button");
    removeButton.classList.add("btn", "btn-secondary", "btn__remove-list");
    removeButton.innerHTML = `
      <i class="fa-regular fa-trash-can"></i>
        Xóa tất cả
    `;
    removeButton.addEventListener("click", async () => {
        const user = auth.currentUser;
        const listRef = doc(db, "users", user.uid);
        const listData = (await getDoc(listRef)).data();

        // Check which list this button belongs to
        let listKey;
        if (listId === "#nav__favorite-list") {
            listKey = "favorites";
        } else if (listId === "#nav__bookmark-list") {
            listKey = "bookmarks";
        } else if (listId === "#nav__history-list") {
            listKey = "history";
        }
        if (listData && listData[listKey] && listData[listKey].length > 0) {
            // If the list exists, delete all items in it
            await updateDoc(listRef, {
                [listKey]: [],
            });

            // Remove all items from the list view
            const list = document.querySelector(listId);
            while (list.firstChild) {
                list.removeChild(list.firstChild);
            }

            // Add a message to show that the list is empty
            const p = document.createElement("p");
            p.innerHTML = `Danh sách trống`;
            list.appendChild(p);
        }
    });
    // Add the remove button to the list if there are items in it
    const list = document.querySelector(listId);
    if (items.length !== 0) {
        list.appendChild(removeButton);
    }

    const btnRemoves = document.querySelectorAll(".btn-remove");
    btnRemoves.forEach((btn) => {
        btn.addEventListener("click", async () => {
            const parent = btn.closest(".card");
            const id = parent.id;
            const listItem = document.querySelector(listId);
            let index = items.indexOf(id);
            if (index !== -1) {
                items.splice(index, 1);
                const list = parent.dataset.list;
                if (listItem.contains(parent)) {
                    listItem.removeChild(parent);
                }
                const user = auth.currentUser;
                console.log(user);
                if (user) {
                    const userId = user.uid;
                    const listRef = doc(db, "users", userId);
                    const docSnap = await getDoc(listRef);
                    if (docSnap.exists()) {
                        const data = docSnap.data();
                        const favorites = data.favorites || [];
                        const bookmarks = data.bookmarks || [];
                        const history = data.history || [];

                        if (list === "favorites") {
                            favorites.splice(favorites.indexOf(id), 1);
                            await updateDoc(listRef, { favorites });
                        } else if (list === "bookmarks") {
                            bookmarks.splice(bookmarks.indexOf(id), 1);
                            await updateDoc(listRef, { bookmarks });
                        } else if (list === "history") {
                            history.splice(history.indexOf(id), 1);
                            await updateDoc(listRef, { history });
                        }
                    }
                }
            }
            if (items.length === 0) {
                const list = document.querySelector(listId);
                const p = document.createElement("p");
                p.innerHTML = `Danh sách trống!`;
                list.removeChild(removeButton);
                list.appendChild(p);
            }
        });
    });

    if (items.length == 0) {
        // If the list is empty, add a message to show that
        const p = document.createElement("p");
        p.innerHTML = `Danh sách trống!`;
        list.appendChild(p);
    }
};

const renderListFirebase = async () => {
    onAuthStateChanged(auth, (user) => {
        if (user) {
            const userId = user.uid;
            const userInfo = (user) => {
                const dateJoin = new Date(
                    user.metadata.creationTime
                ).toLocaleDateString("vi", {
                    day: "2-digit",
                    month: "2-digit",
                    year: "numeric",
                    hour: "numeric",
                    minute: "numeric",
                });
                const date = document.querySelector(".join__date");
                date.innerHTML = dateJoin;
                const email = document.querySelector(".user__email");
                email.innerHTML = user.email;
                const userName = document.querySelector(".user__name");
                userName.innerHTML = `<h1>${user.displayName}</h1>`;
            };
            userInfo(user);
            const listRef = doc(db, "users", userId);
            getDoc(listRef).then((docSnap) => {
                if (docSnap.exists()) {
                    const favorites = docSnap.data().favorites;
                    render(favorites, "#nav__favorite-list", "favorites");

                    const bookmarks = docSnap.data().bookmarks;
                    render(bookmarks, "#nav__bookmark-list", "bookmarks");

                    const history = docSnap.data().history;
                    render(history, "#nav__history-list", "history");
                }
            });
            changePass();
        } else {
            console.log("not user");
        }
    });
};

const makeCard = (data, listId, listName) => {
    const list = document.querySelector(listId);
    const date = new Date(data.release_date);
    const localDate = date.toLocaleDateString("vi");

    if (!list) {
        return; // return early if the list is not found
    }
    const isOverview = () => {
        if (data.overview != "") {
            return data.overview;
        } else {
            return "Nội dung đang được cập nhật.";
        }
    };

    if (data.backdrop_path == null) {
        data.backdrop_path = data.poster_path;
        if (data.backdrop_path == null) {
            return;
        }
    }
    let genreHTML = "";
    data.genres.forEach((genre, i) => {
        if (i < 2)
            genreHTML += `<a href="./search.html?with_genres=${genre.id}" class="card__genres-text" id="${genre.id}">${genre.name}</a>`;
    });

    list.innerHTML += `
  <div class="card m-3" id="${data.id}" data-list="${listName}">
    <div class="row g-0">
        <div class="col-4">
            <a href="./movie.html?${data.id}" title="${data.title}">
                <img src="${api.imgUrlW533}${data.backdrop_path}"
                    class="img-fluid rounded-start" alt="${data.title}">
            </a>
        </div>
        <div class="col-8">
            <div class="card-body">
                <div class="wrap">
                    <div class="card-title">
                        <a href="./movie.html?${data.id}">
                            <h2 title="${data.title}">${data.title}</h2>
                        </a>
                        <p class="release-date">Ngày phát hành: ${localDate}</p>
                    </div>
                    <p class="card-text">${isOverview()}</p>
                    <div class="d-none d-sm-flex">
                        ${genreHTML}
                    </div>
                </div>
                <button class="btn btn-secondary btn-remove" title="xóa">
                    <i class="fa-solid fa-xmark"></i>
                </button>
            </div>
        </div>
    </div>
  </div>
  `;
};

const changePass = () => {
    const user = auth.currentUser;
    hiddenShowForm();
    toggleShowPass();
    hasText();

    const save = document.querySelector(".login__btn-save");
    const btnSave = save.firstElementChild;
    btnSave.addEventListener("click", (e) => {
        let oldPass = document.querySelector("#old__password").value;
        const credential = EmailAuthProvider.credential(user.email, oldPass);

        let newPass = document.querySelector("#new__password").value;
        let confirmPass = document.querySelector("#enter__password").value;

        reauthenticateWithCredential(user, credential)
            .then(() => {
                if (newPass === oldPass) {
                    return showErrorToast(
                        "lỗi",
                        "Mật khẩu mới không được trùng với mật khẩu cũ."
                    );
                } else if (newPass !== confirmPass) {
                    return showErrorToast("Lỗi", "Mật khẩu xác nhận không khớp.");
                } else if (newPass.length < MIN_LENGTH_PASS) {
                    return showWarningToast(
                        `Mật khẩu mới phải có ít nhất ${MIN_LENGTH_PASS} kí tự`
                    );
                } else {
                    // Thực hiện cập nhật mật khẩu
                    updatePassword(user, newPass)
                        .then(() => {
                            showSuccessToast("Đổi mật khẩu thành công!");
                            save.nextElementSibling.click();
                        })
                        .catch((error) => {
                            console.error(error);
                            showInfoToast("Đã xảy ra lỗi khi cập nhật mật khẩu");
                        });
                }
            })
            .catch((err) => {
                showInfoToast("Mật khẩu cũ không chính xác");
                console.error(err);
            });
    });
};

const hiddenShowForm = () => {
    const showChange = document.querySelector(".btn__changePassword");
    const btnCancer = document.querySelector(".login__btn-cancel");
    const input = document.querySelector("#new__password");

    showChange.addEventListener("click", () => {
        showChange.nextElementSibling.classList.toggle("d-none");
    });

    btnCancer.addEventListener("click", () => {
        btnCancer.closest(".changePassword__form").classList.toggle("d-none");
        input.value = "";
        input.style.border = "0";
    });
};

