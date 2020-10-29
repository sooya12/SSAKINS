<template>
<div>
    <div id = "header">
        <div id = "left" style="float:left">
            <div id = "logo" style="float:left">
                <img :src="require('@/assets/ssakins.jpg')" style="">
            </div>
            <div id = "ssakins" style="float:left">
                <p>SSAKINS</p>
            </div>
        </div>
        <div id = "right" style="float:right">
            <div id = "profile" style="float:left; margin-top:30px; margin-left:50px;">
                 <!-- <v-row justify="space-around">
                    <v-avatar
                    color="indigo"
                    size="64"
                    >
                    <img :src="require('@/assets/profile.jpg')" style="">
                    </v-avatar>
                 </v-row> -->
                 <v-icon size="60" style="float:center;">mdi-account-circle</v-icon>
            </div>
            <div id = "name" style="float:left; margin-top:50px; margin-left:50px;">
                <h3>감자</h3>
            </div>
            <div id = "logout" style="float:left; margin-left:50px; margin-top:45px;">
                <v-row
                    align="center"
                    justify="space-around"
                >
                <v-btn text>
                    logout
                </v-btn>
                </v-row>
            </div>
        </div>
    </div>
     <div id = "line">
    <v-progress-linear
      color="blue lighten-5"
      rounded
      value="100"
    ></v-progress-linear>
     </div>
</div>
</template>

<script>
export default {

}
</script>

<style>
    #header{
        width: 100%;
        height: 100px;
    }

    img {
        width: 100px;
        margin: 0 auto;
        text-align: center;
    }

    p {
        font-size: 70px;
    }

    h5{
        text-align: center;
    }

    #line{
        width: 100%;
    }
</style>