let vue = new Vue({
    el: '#app',
    data: {
        file: null
    },

    methods: {
        async addPhoto() {
            const formData = new FormData();
            formData.append("file", event.target.files[0]);
            await axios.post('vision/recognize', formData,{
                headers:{'Content-Type':'multipart/form-data'}
            })
            this.addPhoto();

        },
    }
});