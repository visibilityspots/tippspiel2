import Route from '@ember/routing/route';

export default Route.extend({
  model() {
    return this.store.findAll('hello').catch(res => console.log(res));
  }
});
