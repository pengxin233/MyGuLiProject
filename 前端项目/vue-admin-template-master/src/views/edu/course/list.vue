<template>
    <div class="app-container">

         <!--查询表单-->
        <el-form :inline="true" class="demo-form-inline">
            <el-form-item>
                <el-input v-model="courseQuery.title" placeholder="课程标题"/>
            </el-form-item>

            <el-form-item>
                <el-select v-model="courseQuery.status" clearable placeholder="课程状态">
                <el-option :value="'Draft'" label="未发布"/>
                <el-option :value="'Normal'" label="已发布"/>
                </el-select>
            </el-form-item>

            <el-form-item label="添加时间">
                <el-date-picker
                v-model="courseQuery.begin"
                type="datetime"
                placeholder="选择开始时间"
                value-format="yyyy-MM-dd HH:mm:ss"
                default-time="00:00:00"
                />
            </el-form-item>
            <el-form-item>
                <el-date-picker
                v-model="courseQuery.end"
                type="datetime"
                placeholder="选择截止时间"
                value-format="yyyy-MM-dd HH:mm:ss"
                default-time="00:00:00"
                />
            </el-form-item>

            <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
            <el-button type="default" @click="resetData()">清空</el-button>
        </el-form>

         <!-- 表格 -->
        <el-table
        v-loading="listLoading"
        :data="list"
        element-loading-text="数据加载中"
        border
        fit
        highlight-current-row>

            <el-table-column
                label="序号"
                width="70"
                align="center">
                <template slot-scope="scope">
                {{ (page - 1) * limit + scope.$index + 1 }}
                </template>
            </el-table-column>

            <el-table-column prop="title" label="课程标题" width="150" />

            <el-table-column label="课程状态" width="150">
                <template slot-scope="scope">
                {{ scope.row.status==='Draft'?'未发布':'已发布' }}
                </template>
            </el-table-column>

            <el-table-column prop="lessonNum" label="总课时" width="150" />

            <el-table-column prop="viewCount" label="浏览次数" width="150" />

            <el-table-column prop="price" label="价格/￥" width="150" />
 

            <el-table-column prop="gmtCreate" label="添加时间" width="200"/>

            <el-table-column label="操作" width="200" align="center">
                <template slot-scope="scope">
                <router-link :to="'/course/info/'+scope.row.id">
                    <el-button type="primary" size="medium" icon="el-icon-edit" plain>编辑课程基本信息</el-button>
                </router-link>

                <router-link :to="'/course/chapter/'+scope.row.id">
                    <el-button type="primary" size="medium" icon="el-icon-edit" plain>编辑课程大纲信息</el-button>
                </router-link>

                <el-button style="width:175px" type="danger" size="medium" icon="el-icon-delete" plain @click="deleteCourse(scope.row.id)">删除课程信息</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页 -->
        <el-pagination
        :current-page="page"
        :page-size="limit"
        :total="total"
        style="padding: 30px 0; text-align: center;"
        layout="total, prev, pager, next, jumper"
        @current-change="getList"
        />

    </div>
</template>

<script>
//引入teacher.js文件
import course from '@/api/edu/course'
export default {
    data(){//定义当前页面使用数据的遍历或初始值
        return{
            list:{},//查询之后数据返回的结果
            page:1,//当前页
            limit:10,//每页记录数
            total:0,//总记录数
            courseQuery:{}
        }
    },

    created(){//数据渲染之前执行
        this.getList()
    },
    methods:{//创建具体的方法
        //讲师列表的方法
        getList(page = 1) {
            this.page = page
            course.pageCourseCondition(this.page,this.limit,this.courseQuery)
            .then(response => {//请求成功
               this.list = response.data.rows
               this.total = response.data.total
            })
            .catch(error => {
                 console.log(error)
                 })//请求失败
        },

        resetData(){
            this.courseQuery = {}
            //刷新页面
            this.getList()
        },
        //删除课程信息
        deleteCourse(id){
            this.$confirm('此操作将永久删除该讲师记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
                }).then(()=>{
                    course.deleteCourse(id)
                        .then(()=>{
                            //提示信息
                            this.$message({
                                type:'success',
                                message:'删除成功'
                            })
                            //刷新页面
                            this.getList()
                        })
                })
            
        }
    }
}
</script>