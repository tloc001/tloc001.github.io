<template>
  <div class="container-fluid row">
    <div class="aside col-3">
      <Dashboard :listDashBoard="listDashBoard"></Dashboard>
    </div>
    <div class="article col-9">
      <form
        @submit.prevent="props.action === 'create' ? submitForm() : submitUpdateForm()"
      >
        <div
          class="mb-3"
          :style="(props.action === 'view' || props.action === 'create') ? ' display:none;' : ''"
        >
          <label :for="id" class="form-label text-capitalize"></label>
          <input
            :id="id"
            v-model="formData.id"
            v-if="props.action !== 'create'"
            :hidden="props.action === 'view'"
            type="number"
            class="form-control"
            :placeholder="`Enter id`"
          />
        </div>
        <div class="mb-3">
          <label :for="amount" class="form-label text-capitalize">amount</label>
          <input
            :id="amount"
            v-model="formData.amount"
            type="number"
            class="form-control"
            :placeholder="`Enter amount`"
          />
        </div>

        <div class="mb-3">
          <label :for="transactionType" class="form-label text-capitalize">transactionType</label>
          <input
            :id="transactionType"
            v-model="formData.transactionType"
            type="text"
            class="form-control"
            :placeholder="`Enter transactionType`"
          />
        </div>

        <div class="mb-3">
          <label :for="relatedWalletId" class="form-label text-capitalize">relatedWalletId</label>
          <input
            :id="relatedWalletId"
            v-model="formData.relatedWalletId"
            type="number"
            class="form-control"
            :placeholder="`Enter relatedWalletId`"
          />
        </div>

        <div class="mb-3">
          <label :for="description" class="form-label text-capitalize">description</label>
          <input
            :id="description"
            v-model="formData.description"
            type="text"
            class="form-control"
            :placeholder="`Enter description`"
          />
        </div>


        <button
          type="submit"
          :disable="props.action == 'view'"
          class="btn btn-primary"
        >
          <span v-if="props.action === 'create'">Create</span>
          <span v-else-if="props.action === 'create'">Create</span>
          <span v-else>Update</span>
        </button>
      </form>
    </div>
  </div>
</template>
<style></style>

<script setup>
    import { reactive, ref, onMounted, watch } from 'vue'
    import { formatDate } from '../../Module/CommonsFunctions.js'
    import Dashboard from '../../Module/DashBoard.vue'
    import createCrudService from '../../../Configs/reusableCRUDService.js'
    import { useRouter } from 'vue-router'
  	const router = useRouter()
    import axios from 'axios'
    import 'bootstrap/dist/js/bootstrap.bundle.min.js'
    import 'bootstrap/dist/css/bootstrap.min.css'
    const props = defineProps({
        TableName: {
            type: String,
            required: true
        },
        action: {
            type: String,
            required: true
        },
        id: {
            type: String,
            required: true
        }
    })
    const formTableService = createCrudService(props.TableName);

    const formData = reactive({
    			id: '',
    			amount: '',
    			transactionType: '',
    			relatedWalletId: '',
    			description: '',
    			createdAt: '',
    })

    const listDashBoard = [
    	"Accounts",
    	"Authorities",
    	"BaseProducts",
    	"Cancels",
    	"CartItems",
    	"Catalogs",
    	"Categories",
    	"CostHistories",
    	"Coupons",
    	"News",
    	"OrderItems",
    	"Orders",
    	"PaymentMethods",
    	"PriceHistories",
    	"ProductImages",
    	"ProductItems",
    	"PromotionProduct",
    	"Promotions",
    	"PurchaseOrderItems",
    	"PurchaseOrders",
    	"Returns",
    	"Reviews",
    	"Roles",
    	"ShippingMethods",
    	"UserAddresses",
    	"VariantValues",
    	"Variants",
    ]



    async function submitUpdateForm() {
    console.log(formData)
    try {
      const response = await formTableService.update(props.id, formData)
      console.log('Insert successful:', response.data)
      router.push(`/Admin/${props.TableName}`)
    } catch (error) {
      console.error('Insert failed:', error)
    }
  }


  async function submitForm() {
    console.log(formData)
    try {
      const response = await formTableService.post(formData)
      console.log('Insert successful:', response.data)
      router.push(`/Admin/${props.TableName}`)
    } catch (error) {
      console.error('Insert failed:', error)
    }
  }
  const fetchData = async () => {
    if (!props.TableName) return
    try {
      const response = await formTableService.getById(props.id)
      response.data.createdAt = formatDate(response.data.createdAt)
      response.data.updatedAt = formatDate(response.data.updatedAt)
      Object.assign(formData, response.data)
    } catch (err) {
      console.error('Get failed:', err)
    }
  }

    onMounted(fetchData)
    watch(() => props.id, fetchData)
</script>
