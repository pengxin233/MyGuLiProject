<template>
  <div>
    <link rel="stylesheet" href="//g.alicdn.com/de/prismplayer/2.6.0/skins/default/aliplayer-min.css" />

    <script type="text/javascript" src="//g.alicdn.com/de/prismplayer/2.6.0/aliplayer-min.js"></script>


    <!-- 定义播放器dom -->
    <div id="J_prismPlayer" class="prism-player" />
  </div>
</template>

<script>

import vod from '@/api/vod'

export default {
    layout:'video',
//   data(){
//       return{
//           playAuth:'',
//           vid:''
//       }
//   },
//   created(){
//       this.vid = this.$route.params.id
//       this.getAuth()
//   },

//   methods:{
//       getAuth(){
//           vod.getPlayAuth(this.vid)
//             .then(response=>{
//                 this.playAuth = response.data.data.playAuth
//             })
//       }
//   },
//如果使用传统的created的方式，由于mounted不会等待created执行完毕后再执行，会导致mounted中无法使用data中的值
asyncData({ params, error }) {
    return vod.getPlayAuth(params.id).then(response => {
      // console.log(response.data.data)
      return {
        vid: params.id,
        playAuth: response.data.data.playAuth
      }
    })
  },
    /**
 * 页面渲染完成时：此时js脚本已加载，Aliplayer已定义，可以使用
 * 如果在created生命周期函数中使用，Aliplayer is not defined错误
 */
mounted() {
    new Aliplayer({
                  id: "J_prismPlayer",
                  autoplay: false,
                  width:"100%",
                  height:"500px",
                  vid: this.vid,
                  playauth: this.playAuth,
                })
}
}
</script>