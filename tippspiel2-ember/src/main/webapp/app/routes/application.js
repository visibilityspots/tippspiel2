import Route from '@ember/routing/route';
import {computed} from '@ember/object';
import {inject} from '@ember/service';
import RSVP from 'rsvp';

export default Route.extend({
  intl: inject(),
  auth: inject(),
  model() {
    return RSVP.hash({
      localeIsEnUs: computed(() =>  {
        return this.get('intl').get('locale')[0] === 'en-us';
      }),
    })
  },
  beforeModel() {
    iziToast.settings({position: 'topRight'});

    try {
      const locale = localStorage.getItem('locale') || 'en-us';
      this.get('intl').setLocale([locale]);
      localStorage.setItem('locale', locale);
    } catch (e) {
      this.get('intl').setLocale(['en-us']);
      localStorage.setItem('locale', 'en-us');
    }
  }
});
