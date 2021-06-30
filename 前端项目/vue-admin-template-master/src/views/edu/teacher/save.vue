<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" :min="0"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">

          <!-- 头衔缩略图 -->
          <pan-thumb :image="teacher.avatar"/>
          <!-- 文件上传按钮 -->
          <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
          </el-button>

          <!--
          v-show：是否显示上传组件
          :key：类似于id，如果一个页面多个图片上传控件，可以做区分
          :url：后台上传的url地址
          @close：关闭上传组件
          @crop-upload-success：上传成功后的回调 -->
          <image-cropper
                        v-show="imagecropperShow"
                        :width="300"
                        :height="300"
                        :key="imagecropperKey"
                        :url="BASE_API+'/eduOss/fileOss'"
                        field="file"
                        @close="close"
                        @crop-upload-success="cropSuccess"/>

      </el-form-item>
      

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

import teacher from '@/api/edu/teacher'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
  components: { ImageCropper, PanThumb },//对组件进行声明
    data(){
        return{
            teacher: {
              //如果在这里给name等赋值，那么就算什么都不输入也可以存入数据库中
                // name: '',
                sort: 0,
                level: 1,
                // career: '',
                // intro: '',
                avatar: 'https://edu-1011010.oss-cn-beijing.aliyuncs.com/2021/03/10/ee82196e1da14da1ac4529e4e3ba478afile.png'//默认头像
              },
            imagecropperShow : false,//上传弹框是否显示
            imagecropperKey : 0,//上传组件key的唯一标识
            BASE_API:process.env.VUE_APP_BASE_API,//获取.env.development中的地址
            saveBtnDisabled: false//保存按钮是否禁用
        }
    },
    created(){
      console.log('create'+this.teacher)
      // 清空数据 ,在视频中需要清空，现在不需要了，可能是最新版优化了的缘故
      // this.teacher = {}
      // this.saveBtnDisabled = false

        //判断路径中是否有id
        if(this.$route.params && this.$route.params.id){
          //从路径获得id
          const id = this.$route.params.id
          this.getTeacher(id)
        }
    },
    //  watch: {
    //   $route(to, from) {
    //     console.log('watch $route')
    //   }
    // },
    methods :{
      //根据讲师id查询
      getTeacher(id){
        teacher.getTeacher(id)
        .then(response=>{
          this.teacher = response.data.teacher
        }).catch((error) => {
        this.$message({
            type: 'error',
            message: '获取数据失败'+error
        })
    })
      },
        //根据id进行修改
        updateTeacher(){
          teacher.updateTeacher(this.teacher)
          .then(()=>{
            //提示信息
               this.$message({
                    type: 'success',
                    message: '修改成功!'
                });

              //回到列表页面 路由跳转
              this.$router.push({path:'/teacher/table'})
          })
          .catch(error=>{
            this.$message({
                    type: 'error',
                    message: '修改失败!'+error
                });
          })
        },
        saveOrUpdate(){
            this.saveBtnDisabled = true
            //判断teacher中id是否存在
            if(this.teacher.id){
              //修改
              this.updateTeacher()
            }else{
              //添加
              this.savaTeacher ()
            }
            
        },
        //保存讲师数据
        savaTeacher(){
            teacher.addTeacher(this.teacher)
            .then(() => {
              //提示信息
               this.$message({
                    type: 'success',
                    message: '添加成功!'
                });

              //回到列表页面 路由跳转
              this.$router.push({path:'/teacher/table'})
            })
            .catch(error=>{
              this.$message({
                    type: 'error',
                    message: '添加失败!'+error
                });
            })
        },
        close(){//关闭弹窗的方法
          this.imagecropperShow = false // 关闭弹框
          //关闭后，对弹框进行初始化,与之前的key不同时则自动初始化
          this.imagecropperKey = this.imagecropperKey+1
        },
        cropSuccess(data){//上传成功后的方法,data 即为response中的data，在组件中已经进行了封装
          this.imagecropperShow = false //关闭弹框
          this.teacher.avatar = data.url // 上传之后显示图片
          //关闭后，对弹框进行初始化,与之前的key不同时则自动初始化
          this.imagecropperKey = this.imagecropperKey+1
        }
    }

}
</script>