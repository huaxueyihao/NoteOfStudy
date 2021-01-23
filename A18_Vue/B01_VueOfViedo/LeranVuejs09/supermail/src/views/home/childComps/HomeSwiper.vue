<template>
        <swiper>
            <swiper-item v-for="item in banners">
                <a :href="item.link">
                    <img :src="item.image" alt="">
                </a>
            </swiper-item>
        </swiper>


</template>

<script>
import {Swiper,SwiperItem} from 'components/common/swiper'


export default {
    name: "Homeswiper",
    components :{
        Swiper,
        SwiperItem
    }, 
}
</script>

<style scoped>

</style>

