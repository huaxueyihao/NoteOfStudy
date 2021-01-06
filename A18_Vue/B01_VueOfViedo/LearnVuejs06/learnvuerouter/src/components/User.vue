<template>
    <div>
       <h2>我是User</h2> 
       <p>我是user内容</p>
       <h2>{{}}</h2>
    </div>
</template>

<script>
export default {
    name: "User",
    computed: {
        userId(){
            return this.$route.params.userId
        }
    },
    created() {
        console.log('User created');
    },


    deactivated(){
        console.log('User deactivated');
    },
}
</script>

<style scoped>

</style>