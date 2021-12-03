<template>
  <div class="system-menu-container">
    <el-dialog
      :title="upload.title"
      v-model="upload.open"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip tip">
          <el-checkbox
            v-model="upload.updateSupport"
          />是否更新已经存在的用户数据
          <el-link
            type="info"
            style="font-size: 12px"
            @click="getImportTemplate"
            >下载模板</el-link
          >
        </div>
        <div class="el-upload__tip tip" style="color: red">
          提示：仅允许导入“xls”或“xlsx”格式文件！
        </div>
      </el-upload>
      <div class="dialog-footer footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { reactive, toRefs, ref, getCurrentInstance } from "vue";
import { Session } from "@/utils/storage";
import { importTemplate } from "@/api/system/user";
export default {
  name: "upload",
  setup() {
    const { proxy } = getCurrentInstance() as any;
    const uploadRef = ref<HTMLElement | null>(null);
    const state = reactive({
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "用户导入",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: Session.get("token") },
        // 上传的地址
        // url: import.meta.env.VITE_API_URL + "/api/system/user/importData",
        url: "/api/system/user/importData",
      },
    });

    // 打开弹窗
    const openDialog = () => {
      state.upload.open = true;
    };

    // 文件上传中处理
    const handleFileUploadProgress = (event: any, file: any, fileList: any) => {
      state.upload.isUploading = true;
    };
    /** 下载模板操作 */
    const getImportTemplate = () => {
      importTemplate().then((response: any) => {
        proxy.download(response.msg);
      });
    };

    // 文件上传成功处理
    const handleFileSuccess = (response: any, file: any, fileList: any) => {
      state.upload.open = false;
      state.upload.isUploading = false;
      proxy.$refs.uploadRef.clearFiles();
      proxy.$alert(response.message, "导入结果", {
        dangerouslyUseHTMLString: true,
      });
      proxy.mittBus.emit("onUploadModule");
    };
    // 提交上传文件
    const submitFileForm = () => {
      proxy.$refs.uploadRef.submit();
    };
    return {
      uploadRef,
      openDialog,
      getImportTemplate,
      handleFileUploadProgress,
      submitFileForm,
      handleFileSuccess,
      ...toRefs(state),
    };
  },
};
</script>