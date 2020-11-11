<template>
  <v-container id="container">
    <div>
      <headers></headers>
    </div>
    <div style="float:center; margin-top:16px;">
      <div
        id="navigator"
        style="float:left; margin-right:20px; margin-left:20px;"
      >
        <navigator></navigator>
      </div>
      <div class="d-grid" style="background-color:#333;">
        <div class="dashboard"></div>
        <div class="hold-block" @click="move('left')">
          <div>
            <div class="block" @click="holdBlock()">
              <div
                v-for="(ns, i) in nextBlockStyle(holdB)"
                :style="ns"
                :key="i"
              ></div>
            </div>
            <div class="title"></div>
            <div class="title">
              <div>Time<br />{{ dashboard.time }}</div>
            </div>
            <div class="title">
              <transition name="slide-up">
                <div :key="dashboard.score">
                  Score<br />{{ dashboard.score }}
                </div>
              </transition>
            </div>
            <div class="title">
              <transition name="slide-up">
                <div :key="dashboard.KO">KO<br />{{ dashboard.KO }}</div>
              </transition>
            </div>
          </div>
        </div>
        <div class="game-screen" @click="spinBlock(true)">
          <table class="game-table" :style="menu ? 'filter: blur(5px)' : ''">
            <tr v-for="(row, ri) in coord" :key="ri">
              <td
                v-for="(col, ci) in row"
                :key="ci"
                :style="tdStyle(ri, ci, col)"
              ></td>
            </tr>
          </table>

          <transition name="float-up">
            <div
              class="combo text-center"
              v-if="dashboard.acc_combo&gt;0"
              :key="dashboard.acc_combo"
              :style="{ color: colors[dropB.idx + 1] }"
            >
              <div class="combo-number">{{ dashboard.acc_combo }}</div>
              <div class="combo-text">combo</div>
            </div>
          </transition>
          <transition name="float-up">
            <div class="KO" v-if="dashboard.KO&gt;0" :key="dashboard.KO">
              KO
            </div>
          </transition>
          <div
            class="menu d-flex flex-column justify-content-center align-items-center"
            v-if="menu"
          >
            <h1 style="text-align:center; color:white;">
              ※주의※
            </h1>
            <h2 style="text-align:center; color:white;">
              지나친 게임은
            </h2>
            <h2 style="text-align:center; color:white;">
              개발에 좋지 않습니다
            </h2>
            <h1 class="btn btn-primary" @click="start()">시작</h1>
            <h1 class="btn btn-info" @click="hint = true">조작법</h1>
          </div>
        </div>
        <audio
          v-if="flag"
          :src="require('@/assets/tetris.mp3')"
          autoplay
          loop
        ></audio>
        <div class="next-block" @click="move('right')">
          <div class="bg-dark">
            <div class="block block-bdr ma-1" v-for="(nb, i) in nextB" :key="i">
              <div
                v-for="(ns, i) in nextBlockStyle(nb)"
                :style="ns"
                :key="i"
              ></div>
            </div>
          </div>
        </div>
        <div class="down-area" @click="spaceDown()"></div>
        <transition name="slide-up">
          <div class="hint" v-if="hint">
            <h3>화면을 탭하거나 방향키를 누르세요</h3>
            <br />
            <h5>1. 왼쪽 이동(왼쪽 화면 또는 왼쪽 방향키)</h5>
            <h5>2. 오른쪽 이동(오른쪽 화면 또는 방향키)</h5>
            <h5>
              3. 시계 방향으로 회전 (중앙 화면 클릭, 위쪽 화살표 또는 X 키)
            </h5>
            <h5>4. 시계 반대 방향으로 회전 (Z 키)</h5>
            <h5>5. 아래로 내리기(SPACE 키)</h5>
            <h5>6. 블록 홀드(왼쪽 상단 클릭 또는 SHIFT 키)</h5>
            <div class="btn-dark" @click="hint = false">OK</div>
          </div>
        </transition>
      </div>
    </div>
  </v-container>
</template>

<script>
import Navigator from "../components/Navigator";
import Header from "../components/Header";
const block = [
  // tetris O
  [
    [
      [0, 4],
      [1, 4],
      [0, 5],
      [1, 5],
    ],
  ],
  // tetris I
  [
    [
      [0, 5],
      [1, 5],
      [2, 5],
      [3, 5],
    ],
    [
      [2, 3],
      [2, 4],
      [2, 5],
      [2, 6],
    ],
  ],
  // tetris S
  [
    [
      [0, 4],
      [1, 4],
      [1, 5],
      [2, 5],
    ],
    [
      [1, 3],
      [0, 4],
      [1, 4],
      [0, 5],
    ],
  ],
  // tetris N
  [
    [
      [1, 4],
      [2, 4],
      [0, 5],
      [1, 5],
    ],
    [
      [0, 3],
      [0, 4],
      [1, 4],
      [1, 5],
    ],
  ],
  // tetris T
  [
    [
      [1, 3],
      [0, 4],
      [1, 4],
      [1, 5],
    ],
    [
      [0, 4],
      [1, 4],
      [2, 4],
      [1, 5],
    ],
    [
      [0, 3],
      [0, 4],
      [1, 4],
      [0, 5],
    ],
    [
      [1, 4],
      [0, 5],
      [1, 5],
      [2, 5],
    ],
  ],
  // tetris P
  [
    [
      [0, 3],
      [0, 4],
      [0, 5],
      [1, 5],
    ],
    [
      [2, 4],
      [0, 5],
      [1, 5],
      [2, 5],
    ],
    [
      [0, 3],
      [1, 3],
      [1, 4],
      [1, 5],
    ],
    [
      [0, 4],
      [1, 4],
      [2, 4],
      [0, 5],
    ],
  ],
  // tetris L
  [
    [
      [1, 3],
      [1, 4],
      [0, 5],
      [1, 5],
    ],
    [
      [0, 4],
      [1, 4],
      [2, 4],
      [2, 5],
    ],
    [
      [0, 3],
      [1, 3],
      [0, 4],
      [0, 5],
    ],
    [
      [0, 4],
      [0, 5],
      [1, 5],
      [2, 5],
    ],
  ],
];
//spin[shape][type][serial][offset y,x]
const spin = [
  // tetris O
  [
    [
      [0, 0],
      [0, 0],
      [0, 0],
      [0, 0],
    ],
  ],
  // tetris I
  [
    [
      [3, -3],
      [2, -2],
      [1, -1],
      [0, 0],
    ],
    [
      [-3, 3],
      [-2, 2],
      [-1, 1],
      [0, 0],
    ],
  ],
  // tetris S
  [
    [
      [2, -1],
      [0, 0],
      [1, -1],
      [-1, 0],
    ],
    [
      [-2, 1],
      [0, 0],
      [-1, 1],
      [1, 0],
    ],
  ],
  // tetris N
  [
    [
      [0, -1],
      [-1, 0],
      [2, -1],
      [1, 0],
    ],
    [
      [0, 1],
      [1, 0],
      [-2, 1],
      [-1, 0],
    ],
  ],
  // tetris T
  [
    [
      [-2, 1],
      [0, 0],
      [0, 0],
      [-1, 0],
    ],
    [
      [1, -1],
      [0, 0],
      [0, 0],
      [0, 0],
    ],
    [
      [0, 1],
      [-1, 1],
      [-1, 1],
      [1, 0],
    ],
    [
      [1, -1],
      [1, -1],
      [1, -1],
      [0, 0],
    ],
  ],
  // tetris P
  [
    [
      [1, 1],
      [-1, 1],
      [0, 0],
      [0, 0],
    ],
    [
      [-1, -1],
      [2, -2],
      [1, -1],
      [0, 0],
    ],
    [
      [-1, 1],
      [-1, 1],
      [0, 0],
      [-2, 0],
    ],
    [
      [1, -1],
      [0, 0],
      [-1, 1],
      [2, 0],
    ],
  ],
  // tetris L
  [
    [
      [-2, 1],
      [-1, 0],
      [1, -1],
      [0, 0],
    ],
    [
      [1, -1],
      [1, -1],
      [-1, 0],
      [-1, 0],
    ],
    [
      [-1, 1],
      [-2, 2],
      [0, 1],
      [1, 0],
    ],
    [
      [2, -1],
      [2, -1],
      [0, 0],
      [0, 0],
    ],
  ],
];
export default {
  components: {
    navigator: Navigator,
    headers: Header,
  },
  data() {
    return {
      flag: false,
      dropB: null, // {arr:[[0,4],[1,4],[0,5],[1,5]],idx:6,type:0}
      nextB: [],
      holdB: null,
      shadowB: { arr: [] },
      // eslint-disable-next-line no-unused-vars
      coord: new Array(20).fill(0).map((x) => new Array(10).fill(0)),
      //black yellow LightBlue green red pink orange blue
      colors: [
        "transparent",
        "#ffbb33",
        "#33b5e5",
        "#2BBBAD",
        "#CC0000",
        "#9c27b0",
        "#FF8800",
        "#4285F4",
      ],
      dashboard: {
        time: "01:00",
        resTime: 1 * 60 * 1000,
        score: 0,
        combo: false,
        acc_combo: 0,
        KO: 0,
      },
      spawnNewBlock: false,
      interval: 500,
      timeout: null,
      menu: true,
      hint: false,
    };
  },
  methods: {
    spawnBlock() {
      //blocks[shape][type][serial][x,y]
      const newBlock = block.map((x) => x.map((y) => y.map((z) => z.slice())));
      //防止重複
      let candidate = [0, 1, 2, 3, 4, 5, 6].filter(
        (x) => this.nextB.map((x) => x.idx).indexOf(x) === -1
      );
      let idx = candidate[Math.floor(Math.random() * candidate.length)];
      let type = Math.floor(Math.random() * newBlock[idx].length);
      let arr = newBlock[idx][type];
      this.nextB.push({ arr, idx, type });
    },
    nextBlock() {
      this.spawnBlock();
      this.dropB = this.nextB[0];
      this.nextB.shift();
    },
    holdBlock() {
      const newBlock = block.map((x) => x.map((y) => y.map((z) => z.slice())));
      this.hideBlock();
      // 第一次齁住，預留區沒有東西，要生產新的方塊
      if (!this.holdB) {
        let idx = this.dropB.idx;
        let type = this.dropB.type;
        let arr = newBlock[idx][type];
        this.holdB = { arr, idx, type };
        this.nextBlock();
      } else {
        // 第二次以後就跟現有方塊交換
        let idx = this.dropB.idx;
        let type = this.dropB.type;
        let arr = newBlock[idx][type];
        this.dropB = this.holdB;
        this.holdB = { arr, idx, type };
      }
      this.showBlock();
    },
    spinBlock(clockwise) {
      if (!this.spawnNewBlock) {
        this.hideBlock();
        var nextType =
          this.dropB.type + 1 == block[this.dropB.idx].length
            ? 0
            : this.dropB.type + 1;
        var lastType =
          this.dropB.type - 1 < 0
            ? block[this.dropB.idx].length - 1
            : this.dropB.type - 1;
        this.dropB.arr.map((x, i) => {
          if (clockwise) {
            x[0] += spin[this.dropB.idx][this.dropB.type][i][0];
            x[1] += spin[this.dropB.idx][this.dropB.type][i][1];
          } else {
            x[0] -= spin[this.dropB.idx][lastType][i][0];
            x[1] -= spin[this.dropB.idx][lastType][i][1];
          }
        });
        //轉後會不會超過上方或左方或是重疊到現有方塊
        var underTop = this.dropB.arr.every((x) => x[0] >= 0);
        if (
          underTop &&
          this.dropB.arr.every((x) => x[1] >= 0 && this.coord[x[0]][x[1]] == 0)
        ) {
          //成功之後給新的旋轉類型
          this.dropB.type = clockwise ? nextType : lastType;
          this.showBlock();
        } else {
          //不行的話就轉回來，並且向右移動再行測試(recursive)
          this.dropB.arr.map((x, i) => {
            if (clockwise) {
              x[0] -= spin[this.dropB.idx][this.dropB.type][i][0];
              x[1] -= spin[this.dropB.idx][this.dropB.type][i][1];
            } else {
              x[0] += spin[this.dropB.idx][lastType][i][0];
              x[1] += spin[this.dropB.idx][lastType][i][1];
            }
          });
          if (
            underTop &&
            this.dropB.arr.every(
              (x) => x[1] <= 9 && this.coord[x[0]][x[1] + 1] == 0
            )
          ) {
            this.move("right");
            this.spinBlock(clockwise);
          }
        }
      }
    },
    countDown() {
      if (this.dashboard.resTime > 0) {
        this.dashboard.resTime -= 1000;
        var sec = "0" + (Math.floor(this.dashboard.resTime / 1000) % 60);
        var min = "0" + Math.floor(this.dashboard.resTime / 60 / 1000);
        this.dashboard.time = min.substr(-2) + ":" + sec.substr(-2);
        setTimeout(this.countDown, 1000);
      } else {
        clearTimeout(this.timeout);
        this.menu = true;
        this.flag = false;
      }
    },
    main() {
      if (this.spawnNewBlock) {
        this.nextBlock();
        this.dashboard.combo = false;
        this.spawnNewBlock = false;
        if (!this.dropB.arr.every((x) => this.coord[x[0]][x[1]] == 0)) {
          // eslint-disable-next-line no-unused-vars
          this.coord = new Array(20).fill(0).map((x) => new Array(10).fill(0));
          this.dashboard.KO++;
          this.timeout = setTimeout(this.main, 500);
          this.showBlock();
          if (this.dashboard.KO > 2) {
            clearTimeout(this.timeout);
            this.menu = true;
          }
        } else {
          this.showBlock();
          this.main();
        }
      } else {
        this.hideBlock();
        if (
          this.dropB.arr.every(
            (x) => x[0] < 19 && this.coord[x[0] + 1][x[1]] == 0
          )
        ) {
          this.move("down");
        } else {
          this.showBlock();
          this.spawnNewBlock = true;
          this.clearLine();
        }
        this.timeout = setTimeout(this.main, this.interval);
      }
    },
    clearLine() {
      for (let i = 0; i < 20; i++) {
        if (this.coord[i].indexOf(0) === -1) {
          this.coord.splice(i, 1);
          this.coord.unshift([0, 0, 0, 0, 0, 0, 0, 0, 0, 0]);
          this.dashboard.score++;
          this.dashboard.combo = true;
        }
      }
      if (!this.dashboard.combo) {
        this.dashboard.acc_combo = 0;
      }
      this.dashboard.score += this.dashboard.acc_combo;
      if (this.dashboard.combo) {
        this.dashboard.acc_combo++;
      }
    },
    showBlock() {
      this.shadowB = {};
      this.shadowB.arr = this.dropB.arr.map((x) => x.slice());
      while (
        this.shadowB.arr.every(
          (x) => x[0] < 19 && this.coord[x[0] + 1][x[1]] == 0
        )
      ) {
        this.shadowB.arr.map((x) => (x[0] += 1));
      }
      this.dropB.arr.map((x) => {
        this.coord[x[0]][x[1]] = this.dropB.idx + 1;
      });
      this.$set(this.coord, 0, this.coord[0]);
    },
    hideBlock() {
      this.dropB.arr.map((x) => {
        this.coord[x[0]][x[1]] = 0;
      });
    },
    spaceDown() {
      clearTimeout(this.timeout);
      // eslint-disable-next-line no-unused-vars
      this.coord.map((x) => this.move("down"));
      this.clearLine();
      this.spawnNewBlock = true;
      this.main();
    },
    move(direction) {
      if (!this.spawnNewBlock) {
        this.hideBlock();
        switch (direction) {
          case "down":
            if (
              this.dropB.arr.every(
                (x) => x[0] < 19 && this.coord[x[0] + 1][x[1]] == 0
              )
            ) {
              this.dropB.arr.map((x) => (x[0] += 1));
            }
            break;
          case "right":
            if (
              this.dropB.arr.every(
                (x) => x[1] <= 9 && this.coord[x[0]][x[1] + 1] == 0
              )
            ) {
              this.dropB.arr.map((x) => (x[1] += 1));
            }
            break;
          case "left":
            if (
              this.dropB.arr.every(
                (x) => x[1] >= 0 && this.coord[x[0]][x[1] - 1] == 0
              )
            ) {
              this.dropB.arr.map((x) => (x[1] -= 1));
            }
            break;
          default:
            break;
        }
        this.showBlock();
      }
    },
    tdStyle(ri, ci, col) {
      return {
        background: this.colors[col],
        boxShadow:
          col > 0
            ? "0 0 0 5px rgba(0,0,0,0.2) inset"
            : this.shadowB.arr.every((x) => x[0] != ri || x[1] != ci)
            ? ""
            : "0 0 0 5px rgba(255,255,255,0.5) inset",
      };
    },
    nextBlockStyle(nextB) {
      if (nextB) {
        let [yArr, xArr] = [
          nextB.arr.map((x) => x[0]),
          nextB.arr.map((x) => x[1]),
        ];
        let [top, bottom] = [Math.min(...yArr), Math.max(...yArr)];
        let [left, right] = [Math.min(...xArr), Math.max(...xArr)];
        let refs = nextB.arr.map((x) => [x[0] - top, x[1] - left]);
        return refs.map((x) => ({
          width: "25%",
          height: "25%",
          background: this.colors[nextB.idx + 1],
          boxShadow: "0 0 0 5px rgba(0,0,0,0.2) inset",
          position: "absolute",
          top: x[0] * 20 + (3.8 - bottom + top) * 10 + "%",
          left: x[1] * 20 + (3.8 - right + left) * 10 + "%",
        }));
      }
    },
    start() {
      (this.flag = true),
        // eslint-disable-next-line no-unused-vars
        (this.coord = new Array(20).fill(0).map((x) => new Array(10).fill(0)));
      this.dashboard = {
        time: "01:00",
        resTime: 1 * 60 * 1000,
        score: 0,
        combo: false,
        acc_combo: 0,
        KO: 0,
      };
      clearTimeout(this.timeout);
      this.main();
      this.countDown();
      this.menu = false;
    },
  },
  mounted() {
    this.spawnBlock();
    this.spawnBlock();
    this.spawnBlock();
    this.spawnBlock();
    this.spawnBlock();
    this.nextBlock();
    this.showBlock();
    //this.main()
    //this.countDown()
    window.addEventListener("keydown", (e) => {
      var key = e.which;
      switch (key) {
        case 16: //shift
          if (!this.spawnNewBlock) this.holdBlock();
          break;
        case 32: //space
          if (!this.spawnNewBlock) this.spaceDown();
          break;
        case 37:
          this.move("left");
          break;
        case 38: //up arrow
          this.spinBlock(true);
          break;
        case 39:
          this.move("right");
          break;
        case 40:
          this.move("down");
          break;
        case 88: //X
          this.spinBlock(true);
          break;
        case 90: //Z
          this.spinBlock(false);
          break;
        default:
          break;
      }
    });
  },
};
</script>

<style>
#container {
  width: 100%;
  min-width: 100px;
}

#navigator {
  width: 20%;
  min-width: 100px;
}

.d-grid {
  width: 74%;
  height: 600px;
  min-width: 200px;
  text-align: center;
  background: #607d8b;
}

table[class~="game-table"] {
  border-collapse: collapse;
  background: #607d8b;
}
td {
  width: 30px;
  height: 30px;
}

.d-flex {
  display: flex;
}
.flex-column {
  flex-direction: column;
}

.justify-content-center {
  justify-content: center;
}

.align-items-center {
  align-items: center;
}
.text-center {
  text-align: center;
}
.block {
  position: relative;
  width: 100px;
  height: 100px;
  border-radius: 5px;
  overflow: hidden;
  margin: 10px;
  box-shadow: 0 0 0 5px rgba(255, 255, 255, 0.5) inset;
}

.title {
  width: 100px;
  height: 100px;
  font-size: 24px;
  font-weight: bold;
  color: #fff;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
}

.button {
  width: 100%;
  height: 100%;
  background: #666;
  color: #fff;
}

.hold-block {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.game-screen {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
}

.next-block {
  border-radius: 15px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.bg-dark {
  background: #263238;
}

.hint {
  position: fixed;
  width: 100%;
  bottom: 0;
  margin: auto;
  background: #fff;
  padding: 20px;
  max-width: 760px;
}

.combo {
  position: absolute;
  top: 30%;
  left: 50%;
  transform: translate(-50%, -50%) rotate(-5deg);
  font-weight: bold;
  opacity: 0;
}

.combo-number {
  font-size: 72px;
  text-shadow: 5px 5px 10px rgba(0, 0, 0, 0.5);
}

.combo-text {
  font-size: 54px;
  text-shadow: 5px 5px 10px rgba(0, 0, 0, 0.5);
}

.KO {
  position: absolute;
  top: 50%;
  left: 50%;
  color: red;
  opacity: 0;
  text-shadow: 0 0 10px black;
  transform: translate(-50%, -50%);
  font-weight: bold;
  font-size: 108px;
}

.menu {
  position: absolute;
  top: 0;
  width: 100%;
  height: 100%;
}

.btn {
  color: #fff;
  padding: 20px;
  border-radius: 10px;
  cursor: pointer;
  margin-top: 20px;
}

.btn-primary {
  background: #009688;
  border: 1px solid #b2dfdb;
}

.btn-info {
  background: #00bcd4;
  border: 1px solid #b2ebf2;
}

.btn-dark {
  padding: 5px 10px;
  background: #333;
  color: #fff;
  cursor: pointer;
}

.float-up-enter-active {
  animation: float-up 0.5s;
}

@keyframes float-up {
  0%,
  60% {
    opacity: 1;
    transform: translate(-50%, -50%) rotate(-5deg);
  }
  100% {
    opacity: 0;
    transform: translate(-50%, -100%) rotate(-5deg);
  }
}

.slide-up-enter-active {
  animation: slide-up 0.2s;
}
.slide-up-leave-active {
  position: absolute;
  animation: slide-down 0.2s reverse;
}

@keyframes slide-up {
  0% {
    transform: translateY(50px);
    opacity: 0;
  }
}

@keyframes slide-down {
  0% {
    transform: translateY(-50px);
    opacity: 0;
  }
}

.d-grid {
  display: grid;
  grid-template-columns: 1fr 3fr 1fr;
  grid-template-rows: 1fr 6fr 1fr;
  grid-column-gap: 0px;
  grid-row-gap: 0px;
}

.dashboard {
  grid-area: 1 / 1 / 2 / 4;
}

.game-screen {
  grid-area: 2 / 2 / 3 / 3;
}

.next-block {
  grid-area: 2 / 3 / 3 / 4;
}

.hold-block {
  grid-area: 2 / 1 / 3 / 2;
}

.down-area {
  grid-area: 3 / 1 / 3 / 4;
}

/* @media only screen and (max-width: 680px) {
  .td {
    width: 20px;
    height: 20px;
  }

  .block {
    width: 80px;
    height: 80px;
  }

  .title {
    width: 80px;
    height: 80px;
    font-size: 16px;
  }
} */
</style>
