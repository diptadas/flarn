<template>
    <div>
        <!-- Header -->
        <div
                class="header pb-8 pt-5 pt-lg-8 d-flex align-items-center header-back rounded mt--7"
        >
            <!-- Mask -->
            <span class="mask bg-gradient-default opacity-8"></span>
            <!-- Header container -->
            <div class="container-fluid d-flex align-items-center">
                <div class="row">
                    <div class="col-lg-12 col-md-12">
                        <h1 class="display-2 text-white  d-none d-lg-block">Hello {{ user.fullName }}</h1>
                        <p class="text-white mt-0 mb-5 d-none d-lg-block">
                            This is your profile page. You can edit your account here
                        </p>
                        <p class="text-white d-lg-none font-weight-bolder mt-6">Hello {{ user.fullName }}</p>
                         <button @click="edit = true" class="btn btn-sm btn-info" v-if="!edit">
          <span class="btn-inner--icon mr-1">
            <i class="fas fa-edit" style="top: 0;"></i>
          </span>
                    Edit profile
                </button>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fluid mt--5">
            <div class="row d-flex align-items-center">
                <div
                        class="order-2 mb-5 mb-xl-0 mx-auto col col-12 col-md-8"
                        v-if="!edit"
                >
                    <div class="card card-profile shadow">
                        <div class="row justify-content-center">
                            <div class="col-lg-3 order-lg-2 col-12 d-flex justify-content-center">
                                <div class="card-profile-image" style="width: 10rem; height: 10rem;">
                                        <img
                                                :src="user.dpLink"
                                                class="rounded-circle img-fluid img-thumbnail h-100"
                                                style="height: 100%;"
                                        />
                                </div>
                            </div>
                        </div>
                 
                        <div class="card-body mt--6">
                            <div class="row">
                                <div class="col">
                                    <div
                                            class="card-profile-stats row justify-content-center"
                                    >
                                        <div class="col col-6 col-md-3 mr-0">
                      <span class="heading">
                        {{ user.subscriptions.length }}
                      </span>
                                            <span class="description">Subscriptions</span>
                                        </div>
                                        <div class="col col-6 col-md-3 mr-0">
                      <span class="heading">
                        {{ user.subscribers.length }}
                      </span>
                                            <span class="description">Subscribers</span>
                                        </div>
                                        <div class="col col-6 col-md-3 mr-0">
                      <span class="heading">
                        {{ user.points }}
                      </span>
                                            <span class="description">User Points</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="text-center">
                                <h3>
                                    {{ user.fullName }}
                                    <span class="font-weight-light">, {{ user.userType }}</span>
                                </h3>
                                <div class="h5 font-weight-300">
                                    <i class="ni location_pin mr-2"></i>{{ user.username }}
                                </div>
                                <div class="h5 mt-4">
                                    <i class="ni business_briefcase-24 mr-2"></i
                                    >{{ user.state }} - {{ user.city }}
                                </div>
                                <hr class="my-4"/>
                                <p>
                                    {{ user.biography }}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-12 order-xl-1" v-if="edit">
                    <div class="card bg-secondary shadow">
                        <div class="card-header bg-white border-0">
                            <div class="row align-items-center">
                                <div class="col-8">
                                    <h3 class="mb-0">My account</h3>
                                </div>
                                <div class="col-4 text-right">
                                    <a
                                            @click="edit = false"
                                            class="btn btn-sm btn-primary"
                                            href="#!"
                                    >Cancel</a
                                    >
                                </div>
                            </div>
                        </div>
                        <div class="card-body">
                            <form>
                                <h6 class="heading-small text-muted mb-4">User information</h6>

                                <div class="pl-lg-4">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="form-group">
                                                <label class="form-control-label" for="input-country"
                                                >Profile Picture</label
                                                >
                                                <file-pond
                                                        :files="files"
                                                        :server="uploadImage"
                                                        @init="handleFilePondInit"
                                                        accepted-file-types="image/jpeg, image/png"
                                                        class="pointed"
                                                        name="test"
                                                        ref="pond"
                                                />
                                            </div>
                                        </div>

                                        <div class="col-lg-6">
                                            <div class="form-group">
                                                <label class="form-control-label" for="input-username"
                                                >Phone Number</label
                                                >
                                                <input
                                                        class="form-control form-control-alternative"
                                                        id="input-username"
                                                        placeholder="Phone number"
                                                        type="text"
                                                        v-model="user.phoneNumber"
                                                />
                                            </div>
                                        </div>

                                        <div class="col-lg-6">
                                            <div class="form-group">
                                                <label class="form-control-label" for="input-first-name"
                                                >First name</label
                                                >
                                                <input
                                                        class="form-control form-control-alternative"
                                                        id="input-first-name"
                                                        placeholder="First name"
                                                        type="text"
                                                        v-model="user.fullName"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Address -->
                                <h6 class="heading-small text-muted mb-4">
                                    Contact information
                                </h6>
                                <div class="pl-lg-4">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label class="form-control-label" for="input-address"
                                                >Address</label
                                                >
                                                <input
                                                        class="form-control form-control-alternative"
                                                        id="input-address"
                                                        placeholder="Home Address"
                                                        type="text"
                                                        v-model="user.street"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-lg-4">
                                            <div class="form-group">
                                                <label class="form-control-label" for="input-city"
                                                >City</label
                                                >
                                                <input
                                                        class="form-control form-control-alternative"
                                                        id="input-city"
                                                        placeholder="City"
                                                        type="text"
                                                        v-model="user.city"
                                                />
                                            </div>
                                        </div>
                                        <div class="col-lg-4">
                                            <div class="form-group">
                                                <label class="form-control-label" for="input-country"
                                                >Country</label
                                                >
                                                <input
                                                        class="form-control form-control-alternative"
                                                        id="input-country"
                                                        placeholder="Country"
                                                        type="text"
                                                        value="United States"
                                                />
                                            </div>
                                        </div>
                                        <div class="col-lg-4">
                                            <div class="form-group">
                                                <label class="form-control-label" for="input-country"
                                                >Postal code</label
                                                >
                                                <input
                                                        class="form-control form-control-alternative"
                                                        id="input-postal-code"
                                                        placeholder="Postal code"
                                                        type="number"
                                                        v-model="user.zip"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Description -->
                                <h6 class="heading-small text-muted mb-4">About me</h6>
                                <div class="pl-lg-4">
                                    <div class="form-group">
                                        <label>About Me</label>
                                        <textarea
                                                class="form-control form-control-alternative"
                                                placeholder="A few words about you ..."
                                                rows="4"
                                                v-model="user.biography"
                                        ></textarea>
                                    </div>
                                </div>

                                <div class="d-flex justify-content-between mt-4">
                                    <div>
                                        <button
                                                :disabled="delLoading"
                                                @click="deleteAccount"
                                                class="btn btn-danger d-none"
                                                type="button"
                                        >
                      <span
                              aria-hidden="true"
                              class="spinner-grow spinner-grow-sm"
                              role="status"
                              v-if="delLoading"
                      ></span>
                                            Deactivate Account
                                        </button>
                                    </div>

                                    <div>
                                        <button
                                                :disabled="loading"
                                                @click="updateProfile"
                                                class="btn btn-primary"
                                                type="button"
                                        >
                      <span
                              aria-hidden="true"
                              class="spinner-grow spinner-grow-sm"
                              role="status"
                              v-if="loading"
                      ></span>
                                            Update Profile
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <Delete :action="deleteAction" :content="deleteContent" ref="delete"/>
    </div>
</template>

<script>
    import vueFilePond from "vue-filepond";
    // Import image preview and file type validation plugins
    import FilePondPluginFileValidateType from "filepond-plugin-file-validate-type";
    import FilePondPluginImagePreview from "filepond-plugin-image-preview";
    import Delete from "@/components/utils/Delete.vue";
    import axios from "axios";

    // Create component
    const FilePond = vueFilePond(
        FilePondPluginFileValidateType,
        FilePondPluginImagePreview
    );

    const imageUploadToken = process.env.VUE_APP_FILE_UPLOAD;

    export default {
        name: "Profile",
        data() {
            return {
                delLoading: false,
                loading: false,
                edit: false,
                user: {
                    subscriptions: [],
                    subscribers: []
                },
                deleteContent: {},
                deleteAction: null,
                files: [],
                userId: "",
                uploadImage: {
                    process: this.processImage
                }
            };
        },
        methods: {
            processImage(fieldName, file, metadata, load, error, progress) {
                const token = imageUploadToken;
                const url = "https://api.imgbb.com/1/upload";
                const ext = file.name.split(".").pop();
                const fileName = this.$store.state.username + "." + ext;

                const data = new FormData();
                data.append("key", token);
                data.append("name", fileName);
                data.append("image", file);

                const config = {
                    onUploadProgress: function (progressEvent) {
                        progress(
                            progressEvent.lengthComputable,
                            progressEvent.loaded,
                            progressEvent.total
                        );
                    }
                };

                axios
                    .post(url, data, config)
                    .then(res => {
                        if (res.data.data.medium) {
                            this.user.dpLink = res.data.data.medium.url;
                        } else {
                            this.user.dpLink = res.data.data.image.url;
                        }

                        if (res.data.data.thumb) {
                            this.user.avatarLink = res.data.data.thumb.url;
                        } else {
                            this.user.avatarLink = res.data.data.image.url;
                        }

                        load(res.data.id);
                    })
                    .catch(err => {
                        console.log(err);
                        error("Error uploading file");
                    });
            },
            handleFilePondInit() {
                console.log("FilePond has initialized");

                // FilePond instance methods are available on `this.$refs.pond`
            },
            deleteAccount() {
                this.deleteContent = {
                    name: this.user.fullName
                };
                this.deleteAction = () => this.doDeleteAccount();
                this.$refs["delete"].show();
            },
            doDeleteAccount() {
                const url = "users/current/deactivate";

                this.$http.get(url).then(res => {
                    this.$router.replace({name: "login"});
                });
            },
            updateProfile() {
                if (this.loading) return;
                this.loading = true;
                const url = `users/current`;

                this.$http
                    .post(url, this.user)
                    .then(res => {
                        this.user = res.data;
                        this.edit = false;

                        this.$store.dispatch('getCurrentUser');
                    })
                    .finally(() => (this.loading = false));
            },
            getUserProfile(userId) {
                const url = `users/${userId}`;

                this.$http.get(url).then(res => {
                    this.user = res.data;
                    this.edit = false;
                });
            }
        },
        created() {
            this.userId = Number(this.$store.state.userId);
            this.getUserProfile(this.userId);
        },
        components: {
            Delete,
            FilePond
        }
    };
</script>
