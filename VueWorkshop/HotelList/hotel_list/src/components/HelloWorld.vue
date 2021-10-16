<template>
  <div id="app">
    <h1
      style="
        font-family: 'Times New Roman', sans-serif;
        font-style: italic;
        font-weight: bold;
      "
    >
      Hotel List
    </h1>

    <div id="eltable">
      <el-table
        :data="tableData"
        stripe
        :style="{
          width: '100%',
          align: 'center',
          fontSize: '15px',
          fontFamily: 'Times New Roman',
          fontWeight: 'bold',
        }"
        border
        :row-style="{ height: '20px' }"
        :cell-style="{ padding: '10px' }"
        :header-cell-style="{ 'background-color': '#c6e5ff', color: '#606266' }"
      >
        <el-table-column prop="hotelName" label="Hotel Name" align="center">
        </el-table-column>
        <el-table-column prop="City" label="City" align="center">
        </el-table-column>
        <el-table-column prop="District" label="District" align="center">
        </el-table-column>
        <el-table-column prop="DateValue" label="Date" align="center">
        </el-table-column>
        <el-table-column
          prop="EarCheck"
          label="Earliest check-in time"
          align="center"
          width="180"
        >
        </el-table-column>
        <el-table-column prop="Price" label="Price" align="center">
        </el-table-column>
        <el-table-column prop="RoomType" label="Room Type" align="center">
        </el-table-column>
        <el-table-column label="Operation" align="center">
          <template slot-scope="scope">
            <el-button type="button" @click="deleteHotel(scope.$index)"
              >Delete</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <br />
    <el-button type="button" @click="addHotel">Add Hotel</el-button>
    <br />

    <el-dialog
      class="dialog"
      :visible.sync="AddingState"
      append-to-body
      top="100px"
      custom-class="dialogClass"
    >
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="Hotel Name">
          <el-input
            v-model="formInline.hotelName"
            placeholder="Hotel Name"
            @input="checkHotelName"
          ></el-input>
        </el-form-item>

        <el-form-item label="City">
          <el-select v-model="formInline.City" placeholder="City">
            <el-option label="GUANGZHOU" value="GUANGZHOU"></el-option>
            <el-option label="SHENZHEN" value="SHENZHEN"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="District">
          <el-select
            v-if="formInline.City === ''"
            v-model="formInline.District"
            placeholder="District"
          >
            <el-option> </el-option>
          </el-select>

          <el-select
            v-if="formInline.City === 'GUANGZHOU'"
            v-model="formInline.District"
            placeholder="District"
          >
            <el-option
              v-for="(item, idx) in GZ"
              v-bind:key="idx"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>

          <el-select
            v-if="formInline.City === 'SHENZHEN'"
            v-model="formInline.District"
            placeholder="District"
          >
            <el-option
              v-for="(item, idx) in SZ"
              v-bind:key="idx"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="Date">
          <el-date-picker
            v-model="formInline.DateValue"
            type="date"
            placeholder="Select a date"
            format="yyyy-MM-dd"
            :picker-options="pickerOptions"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>

        <el-form-item label="Earliest check-in time">
          <el-time-picker
            v-model="formInline.EarCheck"
            type="date"
            placeholder="Select a time"
            format="HH:mm"
            value-format="HH:mm"
          >
          </el-time-picker>
        </el-form-item>

        <el-form-item label="Price">
          <el-input
            v-model="formInline.Price"
            placeholder="type in price"
            @input="checkPrice"
          ></el-input>
        </el-form-item>

        <el-form-item label="Room Type">
          <el-select v-model="formInline.RoomType" placeholder="room type">
            <el-option
              v-for="(item, idx) in roomType"
              v-bind:key="idx"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-button type="button" @click="submit">Add Hotel</el-button>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "App",
  data: function () {
    return {
      tableData: [
        {
          hotelName: "Sheraton",
          City: "SHEN ZHEN",
          District: "FU TIAN",
          DateValue: "2021-11-1",
          EarCheck: "14:00",
          Price: "3952",
          RoomType: "Standard Room",
        },
        {
          hotelName: "Intercontinental",
          City: "SHEN ZHEN",
          District: "YAN TIAN",
          DateValue: "2021-11-1",
          EarCheck: "14:00",
          Price: "3999",
          RoomType: "King Bed Room",
        },
      ],
      formInline: {
        hotelName: "",
        City: "",
        District: "",
        DateValue: "",
        EarCheck: "14:00",
        Price: "",
        RoomType: "",
      },
      AddingState: false,

      SZ: [
        "LUO HU",
        "FU TIAN",
        "NAN SHAN",
        "YAN TIAN",
        "LONG GANG",
        "LONG HUA",
        "PING SHAN",
        "DA PANG",
        "BAO AN",
        "GUANG MING",
        "SHEN SHAN HE ZUO",
      ],
      GZ: [
        "LI WAN",
        "YUE XIU",
        "HAI ZHU",
        "TIAN HE",
        "BAI YUN",
        "HUANG PU",
        "PAN YU",
        "HUA DU",
        "NAN SHAN",
        "CONG HUA",
        "ZNEG CHENG",
      ],
      roomType: [
        "Standard Room",
        "King Bed Room",
        "Family Room",
        "Presidential Suite",
      ],
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now();
        },
      },
    };
  },
  methods: {
    deleteHotel: function (index) {
      this.tableData.splice(index, 1);
    },

    addHotel: function () {
      this.AddingState = true;
    },

    destory: function () {
      this.AddingState = false;
    },

    submit: function () {
      if (
        this.formInline.hotelName != "" &&
        this.formInline.City != "" &&
        this.formInline.District != "" &&
        this.formInline.DateValue != "" &&
        this.formInline.EarCheck != "" &&
        this.formInline.Price != "" &&
        this.formInline.RoomType != ""
      ) {
        this.AddingState = false;
        this.tableData.push({
          hotelName: this.formInline.hotelName,
          City: this.formInline.City,
          District: this.formInline.District,
          DateValue: this.formInline.DateValue,
          EarCheck: this.formInline.EarCheck,
          Price: this.formInline.Price,
          RoomType: this.formInline.RoomType,
        });
      }
      if (this.judge_from_json()) {
        alert("Add successfully!");
      } else if (!this.judge_from_json()) {
        alert("Couldn't add a duplicated room!");
        this.tableData.splice(-1, 1);
      } else {
        alert("Please fill out the whole form!");
      }
    },

    checkHotelName: function () {
      var reg = /^[ A-Za-z]*$/;
      if (!reg.test(this.formInline.hotelName)) {
        this.formInline.hotelName = "";
        alert("Hotel Name can only be letters!");
      }
    },
    checkPrice: function () {
      var reg = /^[ \d.]*$/;
      if (!reg.test(this.formInline.Price)) {
        this.formInline.Price = "";
        alert("Price can only be a valid number!");
      }
    },
    judge_from_json: function () {
      let len = this.tableData.length;
      for (let i = 0; i < len - 1; i++) {
        for (let j = i + 1; j < len; j++) {
          if (
            this.tableData[i].hotelName == this.tableData[j].hotelName &&
            this.tableData[i].City == this.tableData[j].City &&
            this.tableData[i].District == this.tableData[j].District &&
            this.tableData[i].RoomType == this.tableData[j].RoomType
          ) {
            return false;
          } else if (
            this.tableData[i].hotelName == this.tableData[j].hotelName &&
            this.tableData[i].City == this.tableData[j].City &&
            this.tableData[i].District == this.tableData[j].District &&
            this.tableData[i].RoomType != this.tableData[j].RoomType &&
            this.tableData[i].Price == this.tableData[j].Price
          ) {
            return false;
          }
        }
      }
      return true;
    },
  },
};
</script>

<style scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

#eltable {
  color: #d415bb;
  width: 1200px;
  margin: auto;
}

form {
  font-family: "Times New Roman", sans-serif;
  font-weight: bold;
  line-height: 40px;
  /* width: 400px;
  height: 500px; */
  text-align: center;
  /* background-color: #f9e3af; */
  font-size: 20px;
  margin: 0 auto 0;
  /* border: 2px solid black; */
  /* border-radius: 30px; */
}

.el-dialog.el-dialog--width-auto {
  width: auto !important;
}
</style>
