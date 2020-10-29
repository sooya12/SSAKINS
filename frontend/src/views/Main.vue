<template>
    <v-container>
      <div>
        <headers></headers>
      </div>
        <div style="float:center">
              <div class ="navigator" style="float:left; margin-top:16px; margin-right:40px;">
                <navigator></navigator>
              </div>
            <div class="table" style="float:left; margin-top:16px;">
                <v-data-table v-model="selected" :headers="headers" :items="desserts" item-key="name" show-select="show-select" class="elevation-1">
                    <!-- <template v-slot:top>
                        <v-switch v-model="singleSelect" label="Single select" class="pa-3">123</v-switch>
                    </template> -->
                </v-data-table>
                <v-btn color="primary" style="float:left">
                  생성하기
                </v-btn>
                <v-btn color="error" style="float:right">
                  삭제하기
                </v-btn>
            </div>
        </div>
        <child items></child>
        <span>{{selected}}</span>
        <li v-for="name in selected" :key="name">{{name['name']}}</li>
    </v-container>
</template>

<script>
    import Navigator from "../components/Navigator";
    import Header from "../components/Header";

    export default {
        components: {
            "navigator": Navigator,
            "headers": Header
        },
        props: {
          items: String
        },
        template:'<span>{{items}}</span>',
        data() {
            return {
                selected:[],
                headers: [
                    {
                        text: 'No.',
                        align: 'start',
                        value: 'name'
                    }, {
                        text: '설정 CI/CD 명',
                        value: 'calories'
                    }, {
                        text: '특징',
                        value: 'fat'
                    }, {
                        text: '생성일',
                        value: 'carbs'
                    }, {
                        text: '수정일',
                        value: 'protein'
                    }, {
                        text: '비고',
                        value: 'iron'
                    }
                ],
                desserts: [
                    {
                        name: 'Frozen Yogurt',
                        calories: 159,
                        fat: 6.0,
                        carbs: 24,
                        protein: 4.0,
                        iron: '1%'
                    }, {
                        name: 'Ice cream sandwich',
                        calories: 237,
                        fat: 9.0,
                        carbs: 37,
                        protein: 4.3,
                        iron: '1%'
                    }, {
                        name: 'Eclair',
                        calories: 262,
                        fat: 16.0,
                        carbs: 23,
                        protein: 6.0,
                        iron: '7%'
                    }, {
                        name: 'Cupcake',
                        calories: 305,
                        fat: 3.7,
                        carbs: 67,
                        protein: 4.3,
                        iron: '8%'
                    }, {
                        name: 'Gingerbread',
                        calories: 356,
                        fat: 16.0,
                        carbs: 49,
                        protein: 3.9,
                        iron: '16%'
                    }, {
                        name: 'Jelly bean',
                        calories: 375,
                        fat: 0.0,
                        carbs: 94,
                        protein: 0.0,
                        iron: '0%'
                    }, {
                        name: 'Lollipop',
                        calories: 392,
                        fat: 0.2,
                        carbs: 98,
                        protein: 0,
                        iron: '2%'
                    }, {
                        name: 'Honeycomb',
                        calories: 408,
                        fat: 3.2,
                        carbs: 87,
                        protein: 6.5,
                        iron: '45%'
                    }, {
                        name: 'Donut',
                        calories: 452,
                        fat: 25.0,
                        carbs: 51,
                        protein: 4.9,
                        iron: '22%'
                    }, {
                        name: 'KitKat',
                        calories: 518,
                        fat: 26.0,
                        carbs: 65,
                        protein: 7,
                        iron: '6%'
                    }
                ]
            }
        }
    }
</script>

<style>
    .table {
         width: 70%;
         max-width: 70%;
         text-align: center; 
    }
    .navigator {
         width: 256px;
    }
</style>