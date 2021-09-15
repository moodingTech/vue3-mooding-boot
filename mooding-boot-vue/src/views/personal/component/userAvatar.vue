<template>
  <div class="system-user-container app-container">
    <div class="user-info-head" @click="editCropper()">
      <img
        v-bind:src="options.img"
        title="点击上传头像"
        class="img-circle img-lg"
      />
    </div>
    <el-dialog
      :title="title"
      v-model="open"
      width="800px"
      append-to-body
      @opened="modalOpened"
      @close="closeDialog()"
    >
      <!-- <el-upload
        class="avatar-uploader"
        action="/api/common/uploadImg"
        ref="input"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
      >
        <img v-if="imageUrl" :src="imageUrl" class="avatar" />
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload> -->

      <el-row>
        <el-col :xs="24" :md="12" :style="{ height: '350px' }">
          <vue-cropper
            ref="cropper"
            :aspect-ratio="9 / 9"
            :style="{ height: '100%', width: '100%' }"
            :src="imageUrl"
            preview=".preview"
          />
        </el-col>
        <el-col :xs="24" :md="12">
          <div class="avatar-upload-preview">
            <div class="preview" />
          </div>
        </el-col>
      </el-row>

      <br />
      <el-row>
        <el-col :lg="2" :md="2">
          <el-upload
            action="/api/common/uploadImg"
            :show-file-list="false"
            :before-upload="beforeAvatarUpload"
          >
            <el-button size="small">
              选择
              <i class="el-icon-upload el-icon--right"></i>
            </el-button>
          </el-upload>
        </el-col>
        <el-col :lg="{ span: 1, offset: 2 }" :md="2">
          <el-button
            icon="el-icon-plus"
            size="small"
            @click="zoom(0.2)"
          ></el-button>
        </el-col>
        <el-col :lg="{ span: 1, offset: 1 }" :md="2">
          <el-button
            icon="el-icon-minus"
            size="small"
            @click="zoom(-0.2)"
          ></el-button>
        </el-col>
        <el-col :lg="{ span: 1, offset: 1 }" :md="2">
          <el-button
            icon="el-icon-refresh-left"
            size="small"
            @click="rotate(90)"
          ></el-button>
        </el-col>
        <el-col :lg="{ span: 1, offset: 1 }" :md="2">
          <el-button
            icon="el-icon-refresh-right"
            size="small"
            @click="rotate(-90)"
          ></el-button>
        </el-col>
        <el-col :lg="{ span: 2, offset: 6 }" :md="2">
          <el-button type="primary" size="small" @click="uploadImg()"
            >提 交</el-button
          >
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script >
import VueCropper from "vue-cropperjs";
import "cropperjs/dist/cropper.css";
import { store } from "@/store";
import { uploadAvatar } from "@/api/system/user";
export default {
  components: { VueCropper },
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 是否显示cropper
      visible: false,
      // 弹出层标题
      title: "修改头像",
      options: {
        img: "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2151087692,2403206573&fm=26&gp=0.jpg", //裁剪图片的地址
      },
      previews: {},
      cropImg: null,
      imageUrl: store.state.userInfos.userInfos.avatar, //裁剪图片的地址
    };
  },
  methods: {
    // 打开弹出层结束时的回调
    modalOpened() {
      this.visible = true;
    },
    // 关闭窗口
    closeDialog() {
      this.options.img = store.state.userInfos.userInfos.avatar;
      this.visible = false;
    },
    handleAvatarSuccess(res, file) {
      console.log("上传成功", file);
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      if (isJPG && isLt2M) {
        this.setImage(file);
      }
      return isJPG && isLt2M;
    },
    // 覆盖默认的上传行为
    requestUpload() {},
    // 编辑头像
    editCropper() {
      this.open = true;
    },
    // 上传图片
    uploadImg() {
      this.cropImg = this.$refs.cropper.getCroppedCanvas().toDataURL();
      // base64转blob
      let formData = new FormData();
      formData.append("avatarfile", this.dataURLtoFile(this.cropImg, "test.jpg"));
      uploadAvatar(formData).then((response) => {
        this.open = false;
        this.options.img = process.env.VUE_APP_BASE_API + response.imgUrl;
        store.commit("SET_AVATAR", this.options.img);
        this.msgSuccess("修改成功");
        this.visible = false;
      });
    },
    cropImage() {
      this.cropImg = this.$refs.cropper.getCroppedCanvas().toDataURL();
    },
    // 重置
    reset() {
      this.$refs.cropper.reset();
    },
    rotate(deg) {
      this.$refs.cropper.rotate(deg);
    },
    zoom(percent) {
      this.$refs.cropper.relativeZoom(percent);
    },
    dataURLtoFile(dataurl, filename) {
      let arr = dataurl.split(",");
      let mime = arr[0].match(/:(.*?);/)[1];
      let bstr = atob(arr[1]);
      let n = bstr.length;
      let u8arr = new Uint8Array(n);
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
      }
      return new File(
        [u8arr],
        filename,
        { type: mime },
        { uid: 1111111111111 }
      );
    },
    setImage(e) {
      console.log("改变图片", e);
      const file = e;
      if (file.type.indexOf("image/") === -1) {
        alert("Please select an image file");
        return;
      }
      if (typeof FileReader === "function") {
        const reader = new FileReader();
        reader.onload = (event) => {
          this.imgSrc = event.target.result;
          // rebuild cropperjs with the updated source
          this.$refs.cropper.replace(event.target.result);
        };
        reader.readAsDataURL(file);
      } else {
        alert("Sorry, FileReader API not supported");
      }
    },
  },
};
</script>
<style scoped lang="scss">
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px !important;
  display: block;
}
.preview {
  width: 100%;
  height: calc(200px * (9 / 9)) !important; //
  overflow: hidden;
}
.user-info-head {
  position: relative;
  display: inline-block;
  height: 120px;
}
.user-info-head:hover:after {
  content: "";
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  color: #eee;
  background: rgba(0, 0, 0, 0.5);
  font-size: 24px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  cursor: pointer;
  line-height: 110px;
  border-radius: 50%;
}
</style>